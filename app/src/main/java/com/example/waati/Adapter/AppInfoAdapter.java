package com.example.waati.Adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.waati.Bean.AppInfo;
import com.example.waati.R;

/**
 * Created by 江婷婷 on 2017/10/24.
 */

public class AppInfoAdapter extends BaseAdapter {

    private Context context;
    private List<AppInfo> appInfoList;

    public AppInfoAdapter(Context context, List<AppInfo> appInfoList) {
        this.context = context;
        this.appInfoList = appInfoList;
    }

    private class ViewHolder {
        ImageView appIconImg;
        TextView appNameText;
        TextView appPackageText;
    }

    @Override
    public int getCount() {
        return appInfoList.size();
    }

    @Override
    public Object getItem(int index) {
        return appInfoList.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.app_info_list, null);
            viewHolder.appIconImg = (ImageView) view.findViewById(R.id.app_icon_image_view);
            viewHolder.appNameText = (TextView) view.findViewById(R.id.app_info_name);
            viewHolder.appPackageText = (TextView) view.findViewById(R.id.app_info_package_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (null != appInfoList) {
            viewHolder.appIconImg.setBackground(appInfoList.get(i).getDrawable());
            viewHolder.appNameText.setText(appInfoList.get(i).getAppName());
            viewHolder.appPackageText.setText(appInfoList.get(i).getPackageName());
        }
        return view;

    }


}
