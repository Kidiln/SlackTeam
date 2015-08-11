package com.slack.slackteam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.slack.slackteam.R;

public class SLSplashActivity extends Activity {

    private final int SPLASH_TIME = 1500;
    private Handler mHandler = null;

    private Runnable SplashRunnable = new Runnable() {
        @Override
        public void run() {
            callTeamActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initialiseValues();
    }

    private void initialiseValues() {

        mHandler = new Handler();

        continueLaunch(true);
    }

    private void continueLaunch(boolean isDelayed) {

        if (isDelayed) {
            mHandler.postDelayed(SplashRunnable, SPLASH_TIME);
        } else {
            mHandler.post(SplashRunnable);
        }
    }


    private void callTeamActivity() {

        Intent launchIntent = new Intent(SLSplashActivity.this, SLTeamActivity.class);
        startActivity(launchIntent);
    }

}
