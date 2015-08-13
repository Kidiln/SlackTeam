package com.slack.slackteam.Model;

/**
 * Created by jacobkoikkara on 8/11/15.
 */
public class SLProfile {

//    "profile": {
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

    private String first_name = null;
    private String last_name = null;
    private String real_name = null;
    private String real_name_normalized = null;

    private String title = null;

    private String email = null;
    private String skype = null;
    private String phone = null;

    private String image_24 = null;
    private String image_32 = null;
    private String image_48 = null;
    private String image_72 = null;
    private String image_192 = null;
    private String image_original = null;


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getReal_name_normalized() {
        return real_name_normalized;
    }

    public void setReal_name_normalized(String real_name_normalized) {
        this.real_name_normalized = real_name_normalized;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage_24() {
        return image_24;
    }

    public void setImage_24(String image_24) {
        this.image_24 = image_24;
    }

    public String getImage_32() {
        return image_32;
    }

    public void setImage_32(String image_32) {
        this.image_32 = image_32;
    }

    public String getImage_48() {
        return image_48;
    }

    public void setImage_48(String image_48) {
        this.image_48 = image_48;
    }

    public String getImage_72() {
        return image_72;
    }

    public void setImage_72(String image_72) {
        this.image_72 = image_72;
    }

    public String getImage_192() {
        return image_192;
    }

    public void setImage_192(String image_192) {
        this.image_192 = image_192;
    }

    public String getImage_original() {
        return image_original;
    }

    public void setImage_original(String image_original) {
        this.image_original = image_original;
    }
}
