package com.slack.slackteam.model;

import java.io.Serializable;

/**
 * Created by jacobkoikkara on 8/12/15.
 *
 * POJO class for storing member details.
 */
public class SLTeam implements Serializable{

    private SLMember[] members = null;

    public SLMember[] getMembers() {
        return members;
    }

    public void setMembers(SLMember[] members) {
        this.members = members;
    }
}
