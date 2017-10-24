package com.example.waati.Adapter;

import android.app.usage.UsageStats;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.waati.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 江婷婷 on 2017/10/24.
 */

public class UsageListAdapter extends RecyclerView.Adapter<UsageListAdapter.ViewHolder> {
    private List<UsageStats> mCustomUsageStatsList = new ArrayList<>();
    private DateFormat mDateFormat = new SimpleDateFormat();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mPackageName;
        private final TextView mLastTimeUsed;
        private final TextView mForegroundTime;
        public ViewHolder(View v) {
            super(v);
            mPackageName = (TextView) v.findViewById(R.id.package_name_text_view);
            mLastTimeUsed = (TextView) v.findViewById(R.id.last_time_used_text_view);
            mForegroundTime = (TextView) v.findViewById(R.id.foreground_time_text_view);
        }
        public TextView getLastTimeUsed() {
            return mLastTimeUsed;
        }
        public TextView getPackageName() {
            return mPackageName;
        }
        public TextView getForegroundTime() {
            return mForegroundTime;
        }
    }

    public UsageListAdapter(List<UsageStats> customUsageStatsList) {
        mCustomUsageStatsList = customUsageStatsList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.usage_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getPackageName().setText(mCustomUsageStatsList.get(position).getPackageName());
        long lastTimeUsed = mCustomUsageStatsList.get(position).getLastTimeUsed();
        long foregroundTime = mCustomUsageStatsList.get(position).getTotalTimeInForeground();
        viewHolder.getLastTimeUsed().setText(mDateFormat.format(new Date(lastTimeUsed)));
        viewHolder.getForegroundTime().setText(foregroundTime/60000 + " minutes");
    }

    @Override
    public int getItemCount() {
        return mCustomUsageStatsList.size();
    }

}