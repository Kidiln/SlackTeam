package com.slack.slackteam.dialog;

import android.app.Dialog;
import android.content.Context;

import com.slack.slackteam.model.SLMember;

/**
 * Created by jacobkoikkara on 8/14/15.
 */
public class SLDialogFactory {


    private Context mContext = null;
    private Dialog mSLDialog = null;

    public SLDialogFactory(Context context) {
        this.mContext = context;
    }

    public void showDialogBasedOnType(int dialogType,
                                      SLDialogListener dlgListeners, SLMember member) {

        switch (dialogType) {
            case SLDialogConstants.DialogConstants.DLG_PROGRESS:
                mSLDialog = new SLProgressDialog(mContext).generateDialog();
                break;
            case SLDialogConstants.DialogConstants.DLG_MEMBER:
                mSLDialog = new SLMemberDialog(mContext, member).generateDialog();
                break;

            default:
                break;
        }

        if (mSLDialog != null && !mSLDialog.isShowing()) {
            mSLDialog.show();
        }
    }



    public boolean isSLDialogShowing() {

        if (mSLDialog != null && mSLDialog.isShowing()) {
            return true;
        }
        return false;
    }

    public void dismissSLDialog() {

        if (mSLDialog != null && mSLDialog.isShowing()) {
            mSLDialog.dismiss();
        }
        mSLDialog = null;
    }

}
