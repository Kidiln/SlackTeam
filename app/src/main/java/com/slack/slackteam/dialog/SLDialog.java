package com.slack.slackteam.dialog;

import android.app.Dialog;

/**
 * Interface for showing dialogs. To be used on dialog factory.
 */
public interface SLDialog {

    Dialog generateDialog();

    void setListeners();
}
