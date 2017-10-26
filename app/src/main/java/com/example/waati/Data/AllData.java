package com.example.waati.Data;

import android.app.usage.UsageStats;
import android.util.Log;

import com.example.waati.Bean.AllInfo;
import com.example.waati.Bean.AppInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by 江婷婷 on 2017/10/26.
 */

public class AllData {

    public static List<UsageStats> mUsageStatsList;//所有包的

    public static List<AppInfo> mAppInfoList;

    public static List<AllInfo> mAllInfoList = new ArrayList<>();//信息总和

    public static List<UsageStats> getUsageStats() {
        return mUsageStatsList;
    }

    public static void setUsageStats(List<UsageStats> mUsageStats) {
        AllData.mUsageStatsList = mUsageStats;
    }

    public static List<AppInfo> getAppInfoList() {
        return mAppInfoList;
    }

    public static void setAppInfoList(List<AppInfo> mAppInfoList) {
        AllData.mAppInfoList = mAppInfoList;
    }

    public static void combineToAll() {
        for (AppInfo appInfo :mAppInfoList) {
            AllInfo allInfo = new AllInfo(appInfo);
            for (UsageStats usageStats : mUsageStatsList) {
                if (usageStats.getPackageName().equals(appInfo.getPackageName())) {
                    allInfo.addToList(usageStats);
                    allInfo.addToTime(usageStats.getTotalTimeInForeground());
                }
            }
            allInfo.setLastTimeUsed();
            mAllInfoList.add(allInfo);
        }

        sort();
        java.text.DateFormat mDateFormat = new SimpleDateFormat();

        for (AllInfo a : mAllInfoList) {
            Log.d("aaazzz", "combineToAll: " + a.getAppInfo().getAppName() + " " + formatSeconds(a.getSumTime()/1000) + " "
                    + mDateFormat.format(new Date(a.getLastTimeUsed())));
        }

    }

    /**
     * allInfoList按前台时间排序
     */
    public static void sort() {
        Collections.sort(mAllInfoList, new Comparator<AllInfo>() {
            @Override
            public int compare(AllInfo allInfo, AllInfo t1) {
                if (allInfo.getSumTime() < t1.getSumTime()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    /**
     * 时间换算
     */
    public static String formatSeconds(long seconds) {
        String timeStr = seconds + "秒";
        if (seconds > 60) {
            long second = seconds % 60;
            long min = seconds / 60;
            timeStr = min + "分" + second + "秒";
            if (min > 60) {
                min = (seconds / 60) % 60;
                long hour = (seconds / 60) / 60;
                timeStr = hour + "小时" + min + "分" + second + "秒";
                if (hour > 24) {
                    hour = ((seconds / 60) / 60) % 24;
                    long day = (((seconds / 60) / 60) / 24);
                    timeStr = day + "天" + hour + "小时" + min + "分" + second + "秒";
                }
            }
        }
        return timeStr;
    }

}
