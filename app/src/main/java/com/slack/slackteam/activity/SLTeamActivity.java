package com.slack.slackteam.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.slack.slackteam.BuildConfig;
import com.slack.slackteam.R;
import com.slack.slackteam.adapter.SLTeamImageAdapter;
import com.slack.slackteam.cache.DataCache;
import com.slack.slackteam.cache.ImageCache;
import com.slack.slackteam.cache.ImageFetcher;
import com.slack.slackteam.constants.SLConstants;
import com.slack.slackteam.dialog.SLDialogConstants;
import com.slack.slackteam.dialog.SLDialogFactory;
import com.slack.slackteam.model.SLMember;
import com.slack.slackteam.network.SLTeamList;
import com.slack.slackteam.utils.SLUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class SLTeamActivity extends SLBaseActivity {

    private static final String TAG = "SLTeamActivity";
    private static final String IMAGE_CACHE_DIR = "thumbs";
    private SLDialogFactory mDlgFactory = null;
    private SLMember[] mSLMembers = null;
    private GridView grdSLTeam;
    //    private SLTeamGridAdapter adapter;
    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private SLTeamImageAdapter mAdapter;
    private ImageFetcher mImageFetcher;

    private AdapterView.OnItemClickListener gridItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            SLUtils.showLog(position);
            mDlgFactory.showDialogBasedOnType(SLDialogConstants.DialogConstants.DLG_MEMBER, mImageFetcher, mSLMembers[position]);
        }
    };

    private Callback<SLMember[]> retroCallback = new Callback<SLMember[]>() {
        @Override
        public void success(SLMember[] slMembers, Response response) {
            mSLMembers = slMembers;
            try {
                DataCache.writeObject(SLTeamActivity.this, SLConstants.CACHE_FILE_NAME, slMembers);
            } catch (IOException e) {
                e.printStackTrace();
            }
            setAdapterOnSuccess(slMembers);
        }

        @Override
        public void failure(RetrofitError error) {
            SLUtils.showLog("ERROR", error.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slteam);

        initialiseValues();

        if (SLUtils.isNetworkConnected(SLTeamActivity.this)) {
            callRetroTeamList();
        } else {
            setAdapterOnSuccess(null);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mImageFetcher.setExitTasksEarly(false);
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        mDlgFactory.dismissSLDialog();

        mImageFetcher.setPauseWork(false);
        mImageFetcher.setExitTasksEarly(true);
        mImageFetcher.flushCache();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImageFetcher.closeCache();
    }


    protected void initialiseValues() {

        grdSLTeam = (GridView) findViewById(R.id.grd_team);

        mDlgFactory = getDlgFactoryInstance();

//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//
//        int width = metrics.widthPixels;
//        int height = metrics.heightPixels;
//
//        mImageThumbSize = width/2;
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.grid_thumb_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.grid_thumb_spacing);


        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(SLTeamActivity.this, IMAGE_CACHE_DIR);

        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        mImageFetcher = new ImageFetcher(SLTeamActivity.this, mImageThumbSize);
        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        mImageFetcher.addImageCache(SLTeamActivity.this.getSupportFragmentManager(), cacheParams);

    }

    private void callRetroTeamList() {

        RestAdapter.Builder restBuilder = new RestAdapter.Builder().setEndpoint(SLConstants.LIST_URL);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SLMember[].class, new PhotosDeserializer())
                .create();

        restBuilder.setConverter(new GsonConverter(gson));

        RestAdapter restAdapter = restBuilder.build();

        SLTeamList slTeamList = restAdapter.create(SLTeamList.class);

        slTeamList.getSLTeamList(retroCallback);

        SLUtils.showLog(null, slTeamList.toString());
    }

    private void setAdapterOnSuccess(SLMember[] slMembers) {

        if (slMembers == null) {
            try {
                mSLMembers = (SLMember[]) DataCache.readObject(SLTeamActivity.this, SLConstants.CACHE_FILE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        mAdapter = new SLTeamImageAdapter(SLTeamActivity.this, mImageFetcher, mSLMembers);

        grdSLTeam.setAdapter(mAdapter);
        grdSLTeam.setOnItemClickListener(gridItemListener);

        grdSLTeam.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                // Pause fetcher to ensure smoother scrolling when flinging
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Before Honeycomb pause image loading on scroll to help with performance
                    if (!SLUtils.hasHoneycomb()) {
                        mImageFetcher.setPauseWork(true);
                    }
                } else {
                    mImageFetcher.setPauseWork(false);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });

        // This listener is used to get the final width of the GridView and then calculate the
        // number of columns and the width of each column. The width of each column is variable
        // as the GridView has stretchMode=columnWidth. The column width is used to set the height
        // of each view so we get nice square thumbnails.
        grdSLTeam.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        if (mAdapter.getNumColumns() == 0) {
                            final int numColumns = (int) Math.floor(
                                    grdSLTeam.getWidth() / (mImageThumbSize + mImageThumbSpacing));
                            if (numColumns > 0) {
                                final int columnWidth =
                                        (grdSLTeam.getWidth() / numColumns) - mImageThumbSpacing;
                                mAdapter.setNumColumns(numColumns);
                                mAdapter.setItemHeight(columnWidth);
                                if (BuildConfig.DEBUG) {
                                    Log.d(TAG, "onCreateView - numColumns set to " + numColumns);
                                }
                                if (SLUtils.hasJellyBean()) {
                                    grdSLTeam.getViewTreeObserver()
                                            .removeOnGlobalLayoutListener(this);
                                } else {
                                    grdSLTeam.getViewTreeObserver()
                                            .removeGlobalOnLayoutListener(this);
                                }
                            }
                        }
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_slteam, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class PhotosDeserializer implements JsonDeserializer<SLMember[]> {
        @Override
        public SLMember[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            JsonElement content = json.getAsJsonObject().get("members");

            return new Gson().fromJson(content, SLMember[].class);

        }

    }

}
