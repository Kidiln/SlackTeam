package com.slack.slackteam.model;

import java.io.Serializable;

/**
 * Created by jacobkoikkara on 8/11/15.
 *
 * POJO class for storing member details.
 */
public class SLMember implements Serializable{


    private String id = null;
    private String name = null;
    private boolean deleted = false;
    private String status = null;
    private String color = null;

    private String real_name = null;
    private String tz = null;
    private String tz_label = null;
    private String tz_offset = null;


//    private boolean is_admin = false;
//    private boolean is_owner = false;
//    private boolean is_primary_owner = false;
//    private boolean is_restricted = false;
//    private boolean is_ultra_restricted = false;
//    private boolean has_2fa = false;
//    private boolean has_files = false;

    private SLProfile profile = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getTz_label() {
        return tz_label;
    }

    public void setTz_label(String tz_label) {
        this.tz_label = tz_label;
    }

    public String getTz_offset() {
        return tz_offset;
    }

    public void setTz_offset(String tz_offset) {
        this.tz_offset = tz_offset;
    }

    public SLProfile getProfile() {
        return profile;
    }

    public void setProfile(SLProfile profile) {
        this.profile = profile;
    }
}
