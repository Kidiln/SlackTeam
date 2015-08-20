package com.slack.slackteam.voice;

import android.content.Intent;

/**
 * Created by jacobkoikkara on 8/19/15.
 *
 * Listener for communicating from activity to voice manager.
 */
public interface VoiceActivityToManagerListener {

    public void onActCreateCalled();

    public void onActResumeCalled();

    public void onActPauseCalled();

    public void onActDestroyCalled();

    public void onActActivityResultCalled(int requestCode, int resultCode,
                                          Intent data);

    public void onVoiceInitCalled();

}
