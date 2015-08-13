package com.slack.slackteam.Model;

/**
 * Created by jacobkoikkara on 8/11/15.
 */
public class SLMember {


//    {
//        "id": "U051EEBLW",
//            "name": "amy",
//            "deleted": false,
//            "status": null,
//            "color": "684b6c",
//            "real_name": "Amy Adams",
//            "tz": "America/Los_Angeles",
//            "tz_label": "Pacific Daylight Time",
//            "tz_offset": -25200,
//            "profile": {
//        "first_name": "Amy",
//                "last_name": "Adams",
//                "image_24": "https://s3-us-west-2.amazonaws.com/slack-files2/avatars/2015-05-25/5048495520_dfdf968b935547134126_24.jpg",
//                "image_32": "https://s3-us-west-2.amazonaws.com/slack-files2/avatars/2015-05-25/5048495520_dfdf968b935547134126_32.jpg",
//                "image_48": "https://s3-us-west-2.amazonaws.com/slack-files2/avatars/2015-05-25/5048495520_dfdf968b935547134126_48.jpg",
//                "image_72": "https://s3-us-west-2.amazonaws.com/slack-files2/avatars/2015-05-25/5048495520_dfdf968b935547134126_72.jpg",
//                "image_192": "https://s3-us-west-2.amazonaws.com/slack-files2/avatars/2015-05-25/5048495520_dfdf968b935547134126_192.jpg",
//                "image_original": "https://s3-us-west-2.amazonaws.com/slack-files2/avatars/2015-05-25/5048495520_dfdf968b935547134126_original.jpg",
//                "title": "Marketing",
//                "skype": "",
//                "phone": "415-5559463",
//                "real_name": "Amy Adams",
//                "real_name_normalized": "Amy Adams",
//                "email": "steve+ae1_7@slack-corp.com"
//    },

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
