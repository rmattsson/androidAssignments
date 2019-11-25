package com.example.createnote.ui;

import android.app.Application;

public class NotesApplication extends Application {

    private String URL;
    private String userUuid;
    private String host;
    private String port;

    String ian = "7bdba0fe-fe95-4b1c-8247-f2479ee6e380";
    String usef = "3faa2495-f0f1-4408-ae24-d482f37caf1c";
    String aref = "6e840afc-5c0a-4679-bcfa-8a210e50ecfc";
    String jim = "2c77dafe-1545-432f-b5b1-3a0011cf7036";
    String sandy  = "2c77dafe-1545-432f-b5b1-3a0011cf7036";
    String nobody = "13cea3c0-4b18-471f-9bee-e9060ac62213";

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        host="10.0.2.2";
        port="9999";
        URL = "http://"+host+":"+port;
        userUuid = ian;
    }
}
