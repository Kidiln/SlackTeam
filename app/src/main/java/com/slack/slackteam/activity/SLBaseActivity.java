package com.slack.slackteam.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by jacobkoikkara on 8/11/15.
 */
public class SLBaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialiseValues();
    }

    protected void initialiseValues() {

    }
}
