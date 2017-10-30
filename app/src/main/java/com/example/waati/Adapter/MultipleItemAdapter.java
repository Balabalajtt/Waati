package com.example.waati.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import com.example.waati.Bean.AllThings;
import com.example.waati.Bean.ThingListItem;
import com.example.waati.R;

import java.text.SimpleDateFormat;

/**
 * Created by 江婷婷 on 2017/10/27.
 */

public class MultipleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    public enum ITEM_TYPE {
        ITEM_TYPE_TIME,
        ITEM_TYPE_THING
    }

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    private final LayoutInflater mLayoutInflater;
    private OnItemClickListener onItemClickListener;

    public MultipleItemAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_TIME.ordinal()) {
            View root = mLayoutInflater.inflate(R.layout.time_item, parent, false);
            return new TimeHolder(root);
        } else {
            View root = mLayoutInflater.inflate(R.layout.thing_item, parent, false);
            root.setOnClickListener(this);
            return new ThingHolder(root);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof TimeHolder) {
            ((TimeHolder) holder).mTextView.setText(dateFormat.format(AllThings.sThingList.get(position).getmDate()));
        } else if (holder instanceof ThingHolder) {

            ((ThingHolder) holder).root.setTag(position);
            final ThingListItem item = AllThings.sThingList.get(position);

            ((ThingHolder) holder).mContentText.setText(item.getmContent());
            ((ThingHolder) holder).mTimeText.setText(timeFormat.format(item.getmDate()));
            ((ThingHolder) holder).mCheckBox.setChecked(!(item.getmDoStatus() == 0));
            ((ThingHolder) holder).mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.setmDoStatus(item.getmDoStatus() == 0 ? 1 : 0);
                    AllThings.sThingList.get(position).save();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (AllThings.sThingList.get(position).getmListType() == 1) {
            return ITEM_TYPE.ITEM_TYPE_THING.ordinal();
        } else {
            return ITEM_TYPE.ITEM_TYPE_TIME.ordinal();
        }

    }

    @Override
    public int getItemCount() {
        return AllThings.sThingList.size();
    }


    public static class TimeHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        View root;
        TimeHolder(View view) {
            super(view);
            root = view;
            mTextView = view.findViewById(R.id.date_text);
        }
    }

    public static class ThingHolder extends RecyclerView.ViewHolder {
        TextView mTimeText;
        TextView mContentText;
        CheckBox mCheckBox;
        View root;
        ThingHolder(View view) {
            super(view);
            root = view;
            mTimeText = view.findViewById(R.id.time_text);
            mContentText = view.findViewById(R.id.content_text);
            mCheckBox = view.findViewById(R.id.check_box);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            onItemClickListener.onItemClickListener(view, (Integer) view.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
