package com.slack.slackteam.network;

import com.slack.slackteam.model.SLMember;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by jacobkoikkara on 8/10/15.
 */
public interface SLTeamList {

    @GET("/api/users.list?token=xoxp-5048173296-5048346304-5180362684-7b3865")
    public void getSLTeamList(Callback<SLMember[]> response);


}
