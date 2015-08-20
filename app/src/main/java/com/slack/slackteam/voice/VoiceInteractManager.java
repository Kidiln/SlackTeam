package com.slack.slackteam.voice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

import com.slack.slackteam.constants.SLConstants;
import com.slack.slackteam.model.SLMember;
import com.slack.slackteam.utils.SLUtils;

import java.util.ArrayList;

/**
 * Manager for handling Voice iteraction. The device listens for name mentioned by used and
 * displays corresponding member dialog if found.
 */
public class VoiceInteractManager implements VoiceActivityToManagerListener {


    private Activity mActivity = null;
    private Context mContext = null;
    private Resources resource = null;

    private TextToSpeech mTextSpeech = null;
    private SpeechRecognizer mSpeechRecog = null;
    private SpeechListener speechListener = null;
    private VoiceDialogue voiceInteract = null;
    /**
     * listener used, and callback called, when TTS engine is initialized
     */
    OnInitListener textToSpeechListener = new OnInitListener() {

        @Override
        public void onInit(int status) {

            if (voiceInteract == null) {
                voiceInteract = new VoiceDialogue(mContext, userInitiateTalkListener,
                        mTextSpeech);
            }

            SLUtils
                    .showLog("textToSpeechListener initialized : status "
                            + status);
        }
    };
    private VoiceManagerToActivityListener voiceStartlistener = null;
    private int requestUserCode = 0;
    /**
     * From VoiceDialogue
     */
    VoiceUserListener userInitiateTalkListener = new VoiceUserListener() {
        @Override
        public void callStartListening(int utterCode, boolean isTextReply, int uiToShow) {

            requestUserCode = utterCode;
            startVoiceRecognitionActivity(SLConstants.USER_VOICE_HINT,
                    SLConstants.VOICE_MAX_RESULTS, utterCode,
                    true);
        }

        @Override
        public void setUserSpeakingStatus(boolean speakStatus) {

        }

        @Override
        public void onDeviceReadOut(int utterScenario, int uiToShow) {

        }

        @Override
        public void setIfVerified(boolean isVerified) {

        }
    };
    private SLMember[] mVoiceSLMembers = null;

    public VoiceInteractManager(Context context) {

        this.mContext = context;
        this.mActivity = (Activity) context;

        if (resource == null) {
            resource = mContext.getResources();
        }

        initVoiceFeature();
        if (speechListener == null) {
            speechListener = new SpeechListener(false);
        }

        mSpeechRecog = SpeechRecognizer.createSpeechRecognizer(mContext);
        mSpeechRecog.setRecognitionListener(speechListener);

    }

    public void setmVoiceSLMembers(SLMember[] slMembers) {
        mVoiceSLMembers = slMembers;
    }

    /**
     * Sets the listener for interaction from manager to home activity
     *
     * @param listener : listener passed from activity
     */
    public void setVoiceInteractionForStartListener(VoiceManagerToActivityListener listener) {

        this.voiceStartlistener = listener;
    }

    /**
     * Called from Sms Broadcast Rxr, To start interaction.
     */
    public void startVoiceInteraction() {

        if (voiceInteract != null) {
            voiceInteract.startVoiceGreeting();
        }

    }

    /**
     * Initialise text to speech engine
     */
    private void initVoiceFeature() {
        // Fire off an intent to check if a TTS engine is installed
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        mActivity.startActivityForResult(checkIntent,
                SLConstants.VOICE_CODE);

    }

    /**
     * Called from speechlistener, passes the voice matches to voice interact
     * class
     *
     * @param requestCode : reqCode
     * @param resultCode  : resusltCode
     * @param data        : contains voice result matches
     */
    private void checkSpeechResults(int requestCode, int resultCode, Bundle data) {


        if (requestCode == SLConstants.V_UTTER1 && resultCode == SLConstants.VOICERESULT_OK) {
            ArrayList<String> matches = null;
            int positionOfName = -1;
            if (data == null) {
                matches = new ArrayList<String>();
            } else {
                matches = data
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            }

            for (String temp : matches) {
                SLUtils.showLog("VoiceSuggest: " + temp);
                positionOfName = isNameInMemberList(temp);
                if (positionOfName >= 0) {
                    break;
                }
            }

            if (positionOfName >= 0) {
                voiceStartlistener.showVoiceUIResponse(positionOfName, true);
            } else {
                voiceInteract.endVoiceConversation();
            }

        }
    }

    private int isNameInMemberList(String strName) {

        int count = 0;
        for (SLMember tempMember : mVoiceSLMembers) {
            if (tempMember.getName().equalsIgnoreCase(strName)) {
                return count;
            }
            count++;
        }
        return -1;
    }

    /**
     * Fire an intent to start the speech recognition activity.
     */
    private void startVoiceRecognitionActivity(String hintToUser,
                                               int numberOfResults, int voiceRquestCode,
                                               boolean includeInputCompleteDelay) {

        final Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Specify the calling package to identify your application
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                SLConstants.APP_PACKAGE);

        // Display an hint to the user about what he should say.
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, hintToUser);

        if (includeInputCompleteDelay) {
            // Specify the amount of silence time to determince end of speech
            intent.putExtra(
                    RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,
                    10000);

            // Display an hint to the user about what he should say.
            intent.putExtra(
                    RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,
                    10000);

        }

        // Given an hint to the recognizer about what the user is going to say
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // Specify how many results you want to receive. The results will be
        // sorted
        // where the first result is the one with higher confidence.
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, numberOfResults);

        // mActivity.startActivityForResult(intent, voiceRquestCode);
        speechListener.setIfVerified(false);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSpeechRecog.startListening(intent);

            }
        });
    }

    /**
     * Disables speech recognizer and text to speech component
     */
    private void shutDownVoiceEngine() {
        // Don't forget to shutdown!
        if (mTextSpeech != null) {
            mTextSpeech.stop();
            mTextSpeech.shutdown();
        }

        if (mSpeechRecog != null) {
            mSpeechRecog.stopListening();
            mSpeechRecog.destroy();
        }
    }

    /**
     * Called on OnActivity result of Activity, for handling TextToSpeech engine
     * initialise
     *
     * @param requestCode : reqCode
     * @param resultCode  : resultCode
     * @param data        : data
     */
    private void doOnActivityResult(int requestCode, int resultCode, Intent data) {

        SLUtils.showLog("REQCODE : " + requestCode + "\n resultCODE : "
                + resultCode);

        if (requestCode == SLConstants.VOICE_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                if (mTextSpeech == null) {

                    mTextSpeech = new TextToSpeech(mContext,
                            textToSpeechListener);

                }

            }

        } else {
            // missing data, install it
            Intent installIntent = new Intent();
            installIntent
                    .setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            mContext.startActivity(installIntent);
        }
//		}
    }

    @Override
    public void onActCreateCalled() {

    }

    @Override
    public void onActResumeCalled() {

    }

    @Override
    public void onActPauseCalled() {

    }

    @Override
    public void onActDestroyCalled() {
        shutDownVoiceEngine();
    }

    @Override
    public void onActActivityResultCalled(int requestCode, int resultCode,
                                          Intent data) {
        doOnActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onVoiceInitCalled() {

    }

    /**
     * Listener class for listening user speech. onError and onResult is used to
     * listen end conversation and start appropriate action flow.
     */
    class SpeechListener implements RecognitionListener {

        boolean isResultVerified = false;

        public SpeechListener(boolean isVerified) {
            isResultVerified = isVerified;
        }

        public void setIfVerified(boolean isVerified) {
            isResultVerified = isVerified;
        }

        @Override
        public void onReadyForSpeech(Bundle params) {
            SLUtils.showLog("onReadyForSpeech");
        }

        @Override
        public void onBeginningOfSpeech() {
            SLUtils.showLog("onBeginningOfSpeech");
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            SLUtils.showLog("onBufferReceived");
        }

        @Override
        public void onEndOfSpeech() {
            SLUtils.showLog("onEndOfSpeech");
        }

        @Override
        public void onError(int error) {
            SLUtils.showLog("onError");

            if (!isResultVerified) {
                isResultVerified = true;
                checkSpeechResults(requestUserCode, Activity.RESULT_OK, null);
            }

        }

        @Override
        public void onResults(Bundle results) {
            SLUtils.showLog("onResults");

            if (!isResultVerified) {
                isResultVerified = true;
                checkSpeechResults(requestUserCode, Activity.RESULT_OK, results);
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            SLUtils.showLog("onPartialResults");
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            SLUtils.showLog("onEvent " + eventType);
        }

    }


}
