package com.slack.slackteam.voice;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.speech.RecognizerIntent;

import com.slack.slackteam.utils.SLUtils;

import java.util.List;

public class VoiceInteractUtils {

    private static final String ADDRESS = "address";
    private static final String BODY = "body";
    private static final String PATH_INBOX = "content://sms/inbox";
    private static final String PATH_SENT = "content://sms/sent";

    /**
     * Checks whether the device has Voice Recognition component
     *
     * @param mContext : context passed
     * @return : true, if available
     */
    public static boolean isVoiceRecognitionPresent(Context mContext) {

        // Check to see if a recognition activity is present
        PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

        for (ResolveInfo temp : activities) {
            SLUtils.showLog("ACTION_RECOGNIZE_SPEECH : "
                    + temp.resolvePackageName);
        }
        if (activities.size() != 0) {
            return true;
        }
        return false;
    }

    /**
     * Populates sms to inbox
     *
     * @param mContext : context passed
     * @param number   : number of contact
     * @param body     : body of message
     */
    public static void populateSmsToInbox(Context mContext, String number,
                                          String body) {

        ContentValues values = new ContentValues();
        values.put(ADDRESS, number);
        values.put(BODY, body);
        mContext.getContentResolver().insert(Uri.parse(PATH_INBOX), values);
    }

    /**
     * Populates sms to sent, in inbox
     *
     * @param mContext    : context passed
     * @param telNumber   : number of contact
     * @param messageBody : body of message
     */
    public static void populateSmsToSent(Context mContext, String telNumber,
                                         String messageBody) {
        ContentValues sentSms = new ContentValues();
        sentSms.put(ADDRESS, telNumber);
        sentSms.put(BODY, messageBody);
        ContentResolver contentResolver = mContext.getContentResolver();
        contentResolver.insert(Uri.parse(PATH_SENT), sentSms);
    }

}
