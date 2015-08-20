package com.slack.slackteam.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.slack.slackteam.R;
import com.slack.slackteam.cache.ImageFetcher;
import com.slack.slackteam.model.SLMember;
import com.slack.slackteam.utils.SLUtils;

/**
 * Member dialog used for showing details of selected member.
 */
public class SLMemberDialog implements SLDialog {

    private static final int CLEAR_STR = 0;

    private Context mContext = null;
    private Dialog mSLDialog = null;
    private SLMember mSLMember = null;
    private ImageFetcher mImageFetcher = null;

    private TextView txtFullName = null;
    private ImageView imgMemberIcon = null;
    private TextView txtCall = null;
    private TextView txtTitle = null;
    private TextView txtEmail = null;
    private TextView txtTimeZone = null;

    private StringBuffer strInput = null;

    public SLMemberDialog(Context context, SLMember slMember) {

        this.mContext = context;
        this.mSLMember = slMember;
    }

    public SLMemberDialog(Context context, ImageFetcher imageFetcher, SLMember slMember) {

        this.mContext = context;
        this.mSLMember = slMember;
        this.mImageFetcher = imageFetcher;
    }

    @Override
    public Dialog generateDialog() {

        mSLDialog = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar);
        mSLDialog.setCancelable(true);
        mSLDialog.setContentView(R.layout.dlg_slmember);

        txtFullName = (TextView) mSLDialog.findViewById(R.id.sl_fullname);
        imgMemberIcon = (ImageView) mSLDialog.findViewById(R.id.sl_icon);
        txtTitle = (TextView) mSLDialog.findViewById(R.id.sl_title);
        txtCall = (TextView) mSLDialog.findViewById(R.id.sl_phone);
        txtEmail = (TextView) mSLDialog.findViewById(R.id.sl_email);
        txtTimeZone = (TextView) mSLDialog.findViewById(R.id.sl_timezone);

        updateViews();

        return mSLDialog;
    }

    @Override
    public void setListeners() {

    }

    /**
     * Method for updating view used in dialog layout.
     */
    private void updateViews() {

        SLUtils.showLog(mSLMember.getProfile().getImage_192());
        SLUtils.showLog(mSLMember.getReal_name());

        txtTitle.setText(mSLMember.getProfile().getTitle());
        txtFullName.setText(appendInputToView(txtFullName, mSLMember.getReal_name()));
        txtCall.setText(appendInputToView(txtCall, mSLMember.getProfile().getPhone()));
        txtTimeZone.setText(appendInputToView(txtTimeZone, mSLMember.getTz()));
        txtEmail.setText(appendInputToView(txtEmail, mSLMember.getProfile().getEmail()));

        // Finally load the image asynchronously into the ImageView, this also takes care of
        // setting a placeholder image while the background thread runs
        mImageFetcher.loadImage(mSLMember.getProfile().getImage_192(), imgMemberIcon);


    }

    /**
     * Appends entry to input already present in textview.
     * @param txtView : textview on which entry needs to be appended
     * @param strEntry : Entry to be appended
     * @return : returns the combined entry
     */
    public String appendInputToView(TextView txtView, String strEntry) {
        if (strInput == null) {
            strInput = new StringBuffer(String.valueOf(txtView.getText()));
        } else {
            strInput.setLength(CLEAR_STR);
            strInput.trimToSize();
            strInput.append(String.valueOf(txtView.getText()));
        }
        strInput.append(strEntry);
        return strInput.toString();
    }

    public void setImageFetcherForDialog(ImageFetcher imgFetcher) {
        mImageFetcher = imgFetcher;
    }

}