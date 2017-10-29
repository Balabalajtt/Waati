package com.example.waati.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.waati.Bean.AllThings;
import com.example.waati.Bean.ThingListItem;
import com.example.waati.R;

import java.text.SimpleDateFormat;

/**
 * Created by 江婷婷 on 2017/10/27.
 */

public class MultipleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnLongClickListener {


    public static enum ITEM_TYPE {
        ITEM_TYPE_TIME,
        ITEM_TYPE_THING
    }

    private final LayoutInflater mLayoutInflater;
//    private OnItemLongClickListener mOnItemLongClickListener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
    private OnItemLongClickListener onItemLongClickListener;

    public MultipleItemAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_TIME.ordinal()) {
            View root = mLayoutInflater.inflate(R.layout.time_item, parent, false);
            TimeHolder timeHolder = new TimeHolder(root);
            return timeHolder;
        } else {
            View root = mLayoutInflater.inflate(R.layout.thing_item, parent, false);
            root.setOnLongClickListener(this);
            ThingHolder thingHolder = new ThingHolder(root);
//            root.setOnClickListener(this);
//            root.setOnLongClickListener(this);
            return thingHolder;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TimeHolder) {
            ((TimeHolder) holder).mTextView.setText(dateFormat.format(AllThings.sThingList.get(position).getmDate()));
        } else if (holder instanceof ThingHolder) {
            ((ThingHolder) holder).root.setTag(position);
            final ThingListItem item = AllThings.sThingList.get(position);
            ((ThingHolder) holder).mContentText.setText(item.getmContent());
            ((ThingHolder) holder).mTimeText.setText(timeFormat.format(item.getmDate()));
            if (item.getmDoStatus() == 0) {
                ((ThingHolder) holder).mCheckBox.setChecked(false);
            } else {
                ((ThingHolder) holder).mCheckBox.setChecked(true);
            }
//            if (item.getmEditStatus() == 1) {
//                ((ThingHolder) holder).mTimeText.setVisibility(View.GONE);
//            } else {
//                ((ThingHolder) holder).mTimeText.setVisibility(View.VISIBLE);
//            }
            ((ThingHolder) holder).mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AllThings.sThingList.get(position).getmDoStatus() == 0) {
                        AllThings.sThingList.get(position).setmDoStatus(1);
                    } else {
                        AllThings.sThingList.get(position).setmDoStatus(0);
                    }
                    AllThings.sThingList.get(position).save();
                }
            });
            ((ThingHolder) holder).mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

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
            mTextView = (TextView) view.findViewById(R.id.date_text);
        }
    }


    public static class ThingHolder extends RecyclerView.ViewHolder {
        TextView mTimeText;
        TextView mContentText;
        CheckBox mCheckBox;
        View root;
        LinearLayout mLinearLayout;
        ThingHolder(View view) {
            super(view);
            root = view;
            mTimeText = (TextView) view.findViewById(R.id.time_text);
            mContentText = (TextView) view.findViewById(R.id.content_text);
            mCheckBox = (CheckBox) view.findViewById(R.id.check_box);
        }
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    @Override
    public boolean onLongClick(View view) {
        return onItemLongClickListener != null && onItemLongClickListener.onItemLongClick(view, (Integer) view.getTag());
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

}
