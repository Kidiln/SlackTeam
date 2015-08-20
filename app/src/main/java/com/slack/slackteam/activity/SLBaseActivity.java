package com.slack.slackteam.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.slack.slackteam.dialog.SLDialogFactory;

/**
 * Base Activity class. Properties and values to be used for multiple activities can be implemented here.
 */
public class SLBaseActivity extends ActionBarActivity {


    private SLDialogFactory mDlgFactory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    /**
     * Used for Returning Dialog Factory instance.
     * @return : instance of SLDialogFactory
     */
    protected SLDialogFactory getDlgFactoryInstance() {
        if (mDlgFactory == null) {
            mDlgFactory = new SLDialogFactory(SLBaseActivity.this);
        }
        return mDlgFactory;
    }

}
