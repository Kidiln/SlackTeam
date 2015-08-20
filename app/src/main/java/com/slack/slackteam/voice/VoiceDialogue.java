package com.slack.slackteam.voice;

import android.content.Context;
import android.content.res.Resources;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import com.slack.slackteam.R;
import com.slack.slackteam.constants.SLConstants;

import java.util.HashMap;

/**
 * Created by jacobkoikkara on 8/19/15.
 *
 * Class storing all the conversations to be used for voice interation.
 */
public class VoiceDialogue implements VoiceDialogueListener{

    private TextToSpeech mTtSpeech;
    private Context mContext;
    private Resources resource = null;
    private VoiceUserListener mUserListener = null;


    private HashMap<String, String> map = new HashMap<String, String>();

    public VoiceDialogue(Context context, VoiceUserListener userListener, TextToSpeech textToSpeech) {
        this(context, textToSpeech);
        mUserListener = userListener;

    }

    public VoiceDialogue(Context context, TextToSpeech textToSpeech) {
        mContext = context;
        this.mTtSpeech = textToSpeech;
            resource = mContext.getResources();
    }

    @Override
    public void startVoiceGreeting() {

        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                SLConstants.UTTER1);

        mTtSpeech.speak(resource.getString(R.string.vce_greeting1),
                TextToSpeech.QUEUE_FLUSH,
                null);

        mTtSpeech.playSilence(300, TextToSpeech.QUEUE_ADD, null);

        mTtSpeech.speak(resource.getString(R.string.vce_intro_askname),
                TextToSpeech.QUEUE_ADD, null);

        mTtSpeech.playSilence(10, TextToSpeech.QUEUE_ADD, map);

        mTtSpeech.setOnUtteranceProgressListener(deviceUtterlistener);
    }

    @Override
    public void endVoiceConversation() {

        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                SLConstants.UTTER2);

        mTtSpeech.speak(resource.getString(R.string.vce_sorry1),
                TextToSpeech.QUEUE_FLUSH,
                null);

        mTtSpeech.playSilence(10, TextToSpeech.QUEUE_ADD, map);

        mTtSpeech.setOnUtteranceProgressListener(deviceUtterlistener);
    }

    /**
     * listener for checking the Device utterance. to handle action on the end
     * of device utterance
     */
    UtteranceProgressListener deviceUtterlistener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {

        }

        @Override
        public void onError(String utteranceId) {
        }

        @Override
        public void onDone(String utteranceId) {
            manageUserUtterance(utteranceId);
        }
    };


    /**
     * Method for managing the action to be performed after device communication
     * @param utterID : id of utterance done by device.
     */
    private void manageUserUtterance(String utterID) {
        if(utterID.equalsIgnoreCase(SLConstants.UTTER1)) {
            mUserListener.callStartListening(SLConstants.V_UTTER1, true, SLConstants.V_UTTER1);
        } else if(utterID.equalsIgnoreCase(SLConstants.UTTER2)) {
            // Do Nothing
            // End Conversation
        }
    }
}
