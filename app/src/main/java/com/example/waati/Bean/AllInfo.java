package com.example.waati.Bean;

import android.app.usage.UsageStats;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江婷婷 on 2017/10/24.
 */

public class AllInfo {
    private List<UsageStats> mUsageStatsList = new ArrayList<>();
    private AppInfo mAppInfo;
    private long sumTime;
    private long lastTimeUsed;

    public AllInfo(AppInfo appInfo) {
        mAppInfo = appInfo;
        sumTime = 0;
    }

    public void addToTime(long time) {
        sumTime += time;
    }

    public void addToList(UsageStats usageStats) {
        mUsageStatsList.add(usageStats);
    }

    public AppInfo getAppInfo() {
        return mAppInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        mAppInfo = appInfo;
    }

    public long getSumTime() {
        return sumTime;
    }

    public void setSumTime(long sumTime) {
        this.sumTime = sumTime;
    }

    public long getLastTimeUsed() {
        return lastTimeUsed;
    }

    public void setLastTimeUsed() {
        long lastTime = 0;
        for (UsageStats usageStats : mUsageStatsList) {
            if (lastTime < usageStats.getLastTimeUsed()) {
                lastTime = usageStats.getLastTimeUsed();
            }
        }
        lastTimeUsed = lastTime;
    }
}
