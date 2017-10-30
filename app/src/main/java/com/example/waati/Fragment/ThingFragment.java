package com.example.waati.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.waati.Adapter.MultipleItemAdapter;
import com.example.waati.Bean.AllThings;
import com.example.waati.Bean.ThingListItem;
import com.example.waati.R;


import java.util.Date;


/**
 * Created by 江婷婷 on 2017/10/27.
 */

public class ThingFragment extends Fragment {

    private View rootView;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private LinearLayoutManager mLinearLayoutManager;
    private MultipleItemAdapter mMultipleItemAdapter;
    private FloatingActionButton mButton;
    private static final int EDIT_TYPE = 0;
    private static final int ADD_TYPE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_thing, container,false);
        mContext = getContext();

        mRecyclerView = rootView.findViewById(R.id.thing_recycler_view);
        mButton = rootView.findViewById(R.id.action_button);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mMultipleItemAdapter = new MultipleItemAdapter(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mMultipleItemAdapter);

        //监听
        mMultipleItemAdapter.setOnItemClickListener(new MultipleItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, final int position) {
                mMultipleItemAdapter.notifyDataSetChanged();
                initListener(EDIT_TYPE, position);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initListener(ADD_TYPE, 0);
            }
        });

        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initListener(final int type, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view2 = View.inflate(getContext(), R.layout.input_thing, null);

        final EditText inputDateYear = view2.findViewById(R.id.input_date_year);
        final EditText inputDateMonth = view2.findViewById(R.id.input_date_month);
        final EditText inputDateDay = view2.findViewById(R.id.input_date_day);
        final EditText inputTimeHour = view2.findViewById(R.id.input_time_hour);
        final EditText inputTimeMinute = view2.findViewById(R.id.input_time_minute);
        final EditText inputContent = view2.findViewById(R.id.input_content);

        final Button done = view2.findViewById(R.id.down_button);
        final Button cancel = view2.findViewById(R.id.cancel_button);
        final Button remove = view2.findViewById(R.id.remove_button);

        if(type == ADD_TYPE) {
            remove.setVisibility(View.GONE);
        } else {
            Date date = AllThings.sThingList.get(position).getmDate();

            inputDateYear.setText(date.getYear() + 1900 + "");
            inputDateMonth.setText(date.getMonth() + 1 + "");
            inputDateDay.setText(date.getDate() + "");

            inputTimeHour.setText(date.getHours() + "");
            inputTimeMinute.setText(date.getMinutes() + "");

            inputContent.setText(AllThings.sThingList.get(position).getmContent());

        }

        builder.setView(view2);
        final AlertDialog alertDialog = builder.create();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date newDate;
                try {
                     newDate = new Date(
                            Integer.valueOf(inputDateYear.getText().toString()) - 1900,
                            Integer.valueOf(inputDateMonth.getText().toString()) - 1,
                            Integer.valueOf(inputDateDay.getText().toString()),
                            Integer.valueOf(inputTimeHour.getText().toString()),
                            Integer.valueOf(inputTimeMinute.getText().toString()));
                } catch (Exception e) {
                    newDate = new Date();
                    e.printStackTrace();
                }
                String content = inputContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
                    ThingListItem newItem = new ThingListItem(newDate,
                            content, 1);

                    if (type == EDIT_TYPE) {
                        AllThings.sThingList.get(position).delete();
                        AllThings.changeDown(newItem, position);
                    } else {
                        AllThings.addThing(newItem);
                        newItem.save();
                    }
                    mMultipleItemAdapter.notifyDataSetChanged();
                    alertDialog.dismiss();
                }

            }

        });

        if (type == EDIT_TYPE) {
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AllThings.sThingList.get(position).delete();
                    AllThings.removeThing(position);
                    mMultipleItemAdapter.notifyDataSetChanged();
                    alertDialog.dismiss();
                }
            });
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

}
