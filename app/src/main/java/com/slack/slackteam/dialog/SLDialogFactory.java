package com.slack.slackteam.dialog;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;

import com.slack.slackteam.cache.ImageFetcher;
import com.slack.slackteam.model.SLMember;

/**
 * Factory class for generating dialogs based on type.
 */
public class SLDialogFactory {


    private Context mContext = null;
    private Dialog mSLDialog = null;

    public SLDialogFactory(Context context) {
        this.mContext = context;
    }

    /**
     * Method for showing dialog based on type passed
     * @param dialogType : type of dialog to be shown
     * @param imageFetcher : ImageFetcher to be passed for member dialog.
     * @param member : Details of member to be shown in member dialog.
     */
    public void showDialogBasedOnType(int dialogType,
                                      ImageFetcher imageFetcher, SLMember member) {

        switch (dialogType) {
            case SLDialogConstants.DialogConstants.DLG_PROGRESS:
                mSLDialog = new SLProgressDialog(mContext).generateDialog();
                break;
            case SLDialogConstants.DialogConstants.DLG_MEMBER:
                mSLDialog = new SLMemberDialog(mContext, imageFetcher, member).generateDialog();
                break;

            default:
                break;
        }

        if (mSLDialog != null && !mSLDialog.isShowing()) {
            mSLDialog.show();
        }
    }


    /**
     * Method to check if dialog is being shown
     * @return
     */
    public boolean isSLDialogShowing() {

        if (mSLDialog != null && mSLDialog.isShowing()) {
            return true;
        }
        return false;
    }

    /**
     * Method to dismiss shown dialog.
     */
    public void dismissSLDialog() {

        if (mSLDialog != null && mSLDialog.isShowing()) {
            mSLDialog.dismiss();
        }
        mSLDialog = null;
    }

}
