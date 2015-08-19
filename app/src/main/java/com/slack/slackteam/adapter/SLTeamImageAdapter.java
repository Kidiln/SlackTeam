package com.slack.slackteam.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.slack.slackteam.cache.ImageFetcher;
import com.slack.slackteam.model.SLMember;
import com.slack.slackteam.views.RecyclingImageView;

/**
 * Created by jacobkoikkara on 8/18/15.
 */
public class SLTeamImageAdapter extends BaseAdapter {

    private final Context mContext;
    private int mItemHeight = 0;
    private int mNumColumns = 0;
    private int mActionBarHeight = 0;
    private SLMember[] mSLMembers = null;
    private GridView.LayoutParams mImageViewLayoutParams;
    private ImageFetcher mImageFetcher = null;

    public SLTeamImageAdapter(Context context, ImageFetcher imageFetcher) {
        this(context, imageFetcher, null);
    }

    public SLTeamImageAdapter(Context context, ImageFetcher imageFetcher, SLMember[] slMembers) {
        super();
        mContext = context;
        mImageFetcher = imageFetcher;
        mSLMembers = slMembers;
        mImageViewLayoutParams = new GridView.LayoutParams(
                GridLayout.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT);
        // Calculate ActionBar height
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(
                android.R.attr.actionBarSize, tv, true)) {
            mActionBarHeight = TypedValue.complexToDimensionPixelSize(
                    tv.data, context.getResources().getDisplayMetrics());
        }
    }

    @Override
    public int getCount() {
        // If columns have yet to be determined, return no items
        if (mSLMembers == null || getNumColumns() == 0) {
            return 0;
        }

        // Size + number of columns for top empty row
        return mSLMembers.length;
    }

    @Override
    public Object getItem(int position) {

        return mSLMembers == null ? null : mSLMembers[position].getProfile().getImage_72();
//        return position < mNumColumns ?
//                null : mSLMembers[position - mNumColumns].getProfile().getImage_72();
    }

    @Override
    public long getItemId(int position) {
        return position;// < mNumColumns ? 0 : position - mNumColumns;
    }

//    @Override
//    public int getViewTypeCount() {
//        // Two types of views, the normal ImageView and the top row of empty views
//        return 2;
//    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        //BEGIN_INCLUDE(load_gridview_item)
        // First check if this is the top row
//        if (position < mNumColumns) {
//            if (convertView == null) {
//                convertView = new View(mContext);
//            }
//            // Set empty view with height of ActionBar
//            convertView.setLayoutParams(new AbsListView.LayoutParams(
//                    GridLayout.LayoutParams.MATCH_PARENT, mActionBarHeight));
//            return convertView;
//        }

        // Now handle the main ImageView thumbnails
        ImageView imageView;
        if (convertView == null) { // if it's not recycled, instantiate and initialize
            imageView = new RecyclingImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(mImageViewLayoutParams);
        } else { // Otherwise re-use the converted view
            imageView = (ImageView) convertView;
        }

        // Check the height matches our calculated column width
        if (imageView.getLayoutParams().height != mItemHeight) {
            imageView.setLayoutParams(mImageViewLayoutParams);
        }

        // Finally load the image asynchronously into the ImageView, this also takes care of
        // setting a placeholder image while the background thread runs
//        if(mSLMembers == null) {
//
//        } else {
            mImageFetcher.loadImage(mSLMembers[position].getProfile().getImage_192(), imageView);
//        }
        return imageView;
        //END_INCLUDE(load_gridview_item)
    }

    /**
     * Sets the item height. Useful for when we know the column width so the height can be set
     * to match.
     *
     * @param height
     */
    public void setItemHeight(int height) {
        if (height == mItemHeight) {
            return;
        }
        mItemHeight = height;
        mImageViewLayoutParams =
                new GridView.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, mItemHeight);
        mImageFetcher.setImageSize(height);
        notifyDataSetChanged();
    }

    public void setNumColumns(int numColumns) {
        mNumColumns = numColumns;
    }

    public int getNumColumns() {
        return mNumColumns;
    }
}