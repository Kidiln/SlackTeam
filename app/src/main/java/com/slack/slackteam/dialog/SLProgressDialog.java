package com.slack.slackteam.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.slack.slackteam.R;

/**
 * Dialog for showing progress
 */

public class SLProgressDialog implements SLDialog {

	private Context mContext = null;
	private Dialog mSLDialog = null;

	public SLProgressDialog(Context context) {

		this.mContext = context;
	}

	@Override
	public Dialog generateDialog() {

		mSLDialog = new Dialog(mContext, R.style.Theme_Progress_Dialog);
		mSLDialog.setCancelable(false);
		mSLDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mSLDialog.setContentView(R.layout.dlg_progress);
		return mSLDialog;
	}

	@Override
	public void setListeners() {

	}

}
