package com.slack.slackteam.constants;

/**
 * Constant class. Contains values to be used throught the application.
 */
public class SLConstants {

    public static final String LIST_URL = "https://api.slack.com";

    public static final String CACHE_FILE_NAME = "SlackCache";

    public static final String APP_PACKAGE = "com.slack.slackteam";


    // This code can be any value you want, its just a checksum.
    public static final int VOICE_CODE = 1111;
    public static final int VOICE_MAX_RESULTS = 4;
    public static final int VOICERESULT_OK = -1;

    public static final String UTTER1 = "SL_Greeting";
    public static final int V_UTTER1 = 120;
    public static final String UTTER2 = "SL_ConversationEnd";
    public static final int V_UTTER2 = 121;

    public static final String USER_VOICE_HINT = "Slack Name";
}
