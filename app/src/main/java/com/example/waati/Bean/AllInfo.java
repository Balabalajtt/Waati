package com.example.waati.Bean;

import android.app.usage.UsageStats;

/**
 * Created by 江婷婷 on 2017/10/24.
 */

public class AllInfo {
    private UsageStats mUsageStats;
    private AppInfo mAppInfo;

    public UsageStats getUsageStats() {
        return mUsageStats;
    }

    public void setUsageStats(UsageStats usageStats) {
        mUsageStats = usageStats;
    }

    public AppInfo getAppInfo() {
        return mAppInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        mAppInfo = appInfo;
    }
}
