package com.slack.slackteam.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.slack.slackteam.dialog.SLDialogFactory;

/**
 * Created by jacobkoikkara on 8/11/15.
 */
public class SLBaseActivity extends ActionBarActivity {


    private SLDialogFactory mDlgFactory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    protected SLDialogFactory getDlgFactoryInstance() {
        if (mDlgFactory == null) {
            mDlgFactory = new SLDialogFactory(SLBaseActivity.this);
        }
        return mDlgFactory;
    }

}
