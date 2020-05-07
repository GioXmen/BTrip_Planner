package com.btplanner.btripex;

import android.app.Application;
import android.content.Context;

public class BtripApplication extends Application {
    private static String serverUrl = null;

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public static String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        BtripApplication.serverUrl = serverUrl;
    }
}
