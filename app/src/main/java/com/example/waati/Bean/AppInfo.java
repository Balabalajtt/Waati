package com.example.waati.Bean;

import android.graphics.drawable.Drawable;

/**
 * Created by 江婷婷 on 2017/10/24.
 */

public class AppInfo {

    private String appName;
    private String packageName;
    private Drawable drawable;

    public AppInfo(String appName, String packageName, Drawable drawable) {
        this.appName = appName;
        this.packageName = packageName;
        this.drawable = drawable;
    }

    public String getAppName() {
        if(null == appName)
            return "";
        else
            return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        if(null == packageName)
            return "";
        else
            return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

}
