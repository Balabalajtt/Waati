package com.example.waati.Activity;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.waati.R;
import com.example.waati.Adapter.UsageListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private UsageStatsManager mUsageStatsManager;
    private Button mButton1;
    private Button mButton2;
    private List<UsageStats> mUsageStatsList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private UsageListAdapter mUsageListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsageStatsManager = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
        mUsageStatsList = getUsageStatistics(UsageStatsManager.INTERVAL_DAILY);
        initViews();

    }


    private void initViews() {
        mButton1 = (Button) findViewById(R.id.current_app_button);
        mButton1.setOnClickListener(this);
        mButton2 = (Button) findViewById(R.id.get_access);
        mButton2.setOnClickListener(this);
        mButton2.setVisibility(View.INVISIBLE);
        initRecyclerView();
    }


    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mUsageListAdapter = new UsageListAdapter(mUsageStatsList);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mUsageListAdapter);

    }


    /**
     * 获取当前进程
     * @return 当前进程包名
     */
    private String getForegroundApp() {
        //一天的起始结束值
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        long endTime = calendar.getTimeInMillis();//当前时间
        long startTime = endTime - 2000;//一小段时间

        //获取一小段时间运行过的进程
        List<UsageStats> queryUsageStats = mUsageStatsManager.
                queryUsageStats(UsageStatsManager.INTERVAL_BEST, startTime, endTime);
        if (queryUsageStats == null || queryUsageStats.isEmpty())  {
            return "没获取到";
        }

        //遍历寻找当前进程 最后运行时间最大的进程
        UsageStats recentStats = null;
        for (UsageStats usageStats : queryUsageStats) {
            if(recentStats == null || recentStats.getLastTimeUsed() < usageStats.getLastTimeUsed()) {
                recentStats = usageStats;
            }
        }
        assert recentStats != null;
        return recentStats.getPackageName();

    }


    /**
     * 根据传参周期获得集合
     * @param intervalType 周期类型
     * @return 根据传参计算的UsageStats集合
     */
    public List<UsageStats> getUsageStatistics(int intervalType) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);//一年内的

        List<UsageStats> queryUsageStats = mUsageStatsManager
                .queryUsageStats(intervalType, cal.getTimeInMillis(),
                        System.currentTimeMillis());//按传参的周期计算
        if (queryUsageStats.size() == 0) {
            Log.i(TAG, "权限没开");
            Toast.makeText(this, "未获得权限", Toast.LENGTH_SHORT).show();
            mButton2.setVisibility(View.VISIBLE);
        }
        return queryUsageStats;

    }


    /**
     * 循环输出当前进程
     */
    private void showForegroundApp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.d(TAG, "run: " + getForegroundApp());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.current_app_button:
                showForegroundApp();
                break;
            case R.id.get_access:
                //跳转到获取权限的系统设置页面
                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                break;
        }
    }

}
