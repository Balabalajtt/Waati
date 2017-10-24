package com.example.waati.Activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.waati.Adapter.AppInfoAdapter;
import com.example.waati.Bean.AppInfo;
import com.example.waati.R;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private ListView  appInfoListView;
    private AppInfoAdapter mInfoAdapter;
    private List<AppInfo> mAppInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAppInfoList = getAppInfoList();
        appInfoListView = (ListView)this.findViewById(R.id.app_info_list);
        mInfoAdapter = new AppInfoAdapter(getApplication(), mAppInfoList);
        appInfoListView.setAdapter(mInfoAdapter);
    }


    /**
     * 获取已安装 app信息
     * @return
     */
    public List<AppInfo> getAppInfoList(){
        PackageManager pm = getApplication().getPackageManager();
        List<PackageInfo>  packageInfoList = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);

        for(PackageInfo packageInfo : packageInfoList){
            //标签名 包名 图标
            String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
            String packageName = packageInfo.packageName;
            Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
            AppInfo appInfo = new AppInfo(appName, packageName,drawable);
            mAppInfoList.add(appInfo);
        }
        return mAppInfoList;
    }


}
