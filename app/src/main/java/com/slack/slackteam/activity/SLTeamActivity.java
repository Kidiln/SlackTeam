package com.slack.slackteam.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.slack.slackteam.Model.SLMember;
import com.slack.slackteam.R;
import com.slack.slackteam.adapter.SLTeamGridAdapter;
import com.slack.slackteam.constants.SLConstants;
import com.slack.slackteam.network.SLTeamList;
import com.slack.slackteam.utils.SLUtils;

import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class SLTeamActivity extends SLBaseActivity {


    private GridView grdSLTeam;
    private SLTeamGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slteam);

        callRetroTeamList();
    }

    @Override
    protected void initialiseValues() {
        super.initialiseValues();

        grdSLTeam = (GridView) findViewById(R.id.grd_team);

    }


    private void callRetroTeamList() {

        RestAdapter.Builder restBuilder = new RestAdapter.Builder().setEndpoint(SLConstants.LIST_URL);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SLMember[].class, new PhotosDeserializer())
                .create();

        restBuilder.setConverter(new GsonConverter(gson));

        RestAdapter restAdapter = restBuilder.build();

        SLTeamList slTeamList = restAdapter.create(SLTeamList.class);

        slTeamList.getSLTeamList(new Callback<SLMember[]>() {
            @Override
            public void success(SLMember[] slMembers, Response response) {

                setAdapterOnSuccess(slMembers);
            }

            @Override
            public void failure(RetrofitError error) {
                SLUtils.showLog("ERROR", error.toString());

            }
        });

        SLUtils.showLog(null, slTeamList.toString());
    }

    private void setAdapterOnSuccess(SLMember[] slMembers) {
        adapter = new SLTeamGridAdapter(SLTeamActivity.this, slMembers);
        grdSLTeam.setAdapter(adapter);

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


    class PhotosDeserializer implements JsonDeserializer<SLMember[]>
    {
        @Override
        public SLMember[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            JsonElement content = json.getAsJsonObject().get("members");

            return new Gson().fromJson(content, SLMember[].class);

        }

    }

}
