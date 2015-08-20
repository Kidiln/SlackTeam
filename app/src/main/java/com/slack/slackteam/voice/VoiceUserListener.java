package com.slack.slackteam.voice;

/**
 * Listener for Interacting between VoiceDialogue and VoiceInteractManager.
 */
public interface VoiceUserListener {
	
	public void callStartListening(int utterCode, boolean isTextReply, int uiToShow);
	
	public void setUserSpeakingStatus(boolean speakStatus);
	
	public void onDeviceReadOut(int utterScenario, int uiToShow);
	
	public void setIfVerified(boolean isVerified);

}
