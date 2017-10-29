package com.example.waati.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
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
    private static final String TAG = "ThingFragment";

    private int f = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_thing, container,false);
        mContext = getContext();

        while(f == 0) {
//            initData();
            f = 1;
        }
        Log.d(TAG, "onCreateView: " + AllThings.sThingList.size());

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.thing_recycler_view);
        mButton = rootView.findViewById(R.id.action_button);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mMultipleItemAdapter = new MultipleItemAdapter(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMultipleItemAdapter.setOnItemLongClickListener(new MultipleItemAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, final int position) {
                Toast.makeText(getContext(), AllThings.sThingList.get(position).getmDoStatus() + "", Toast.LENGTH_SHORT).show();
                AllThings.sThingList.get(position).setmEditStatus(1);
                mMultipleItemAdapter.notifyDataSetChanged();


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view2 = View.inflate(getContext(), R.layout.input_thing, null);

                final EditText inputDate = (EditText) view2.findViewById(R.id.input_date);
                final EditText inputTime = (EditText) view2.findViewById(R.id.input_time);
                final EditText inputContent = (EditText) view2.findViewById(R.id.input_content);
                final Button done = (Button) view2.findViewById(R.id.down_button);
                final Button cancel = (Button) view2.findViewById(R.id.cancel_button);
                final Button remove = (Button) view2.findViewById(R.id.remove_button);
                remove.setVisibility(View.VISIBLE);

                inputDate.setText(new SimpleDateFormat("yyyy/MM/dd").format(AllThings.sThingList.get(position).getmDate()));
                inputTime.setText(new SimpleDateFormat("hh:mm").format(AllThings.sThingList.get(position).getmDate()));
                inputContent.setText(AllThings.sThingList.get(position).getmContent());

                // 设置参数
                builder//.setTitle("Login")//.setIcon(R.drawable.ic_launcher)
                        .setView(view2);
                // 创建对话框
                final AlertDialog alertDialog = builder.create();
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] date = inputDate.getText().toString().split("/");
                        String[] time = inputTime.getText().toString().split(":");
                        Date newDate = new Date(Integer.valueOf(date[0]) - 1900,
                                Integer.valueOf(date[1]) - 1, Integer.valueOf(date[2]),
                                Integer.valueOf(time[0]), Integer.valueOf(time[1]));
                        ThingListItem newItem = new ThingListItem(newDate, inputContent.getText().toString(), 1);
                        AllThings.sThingList.get(position).delete();
                        newItem.save();
                        AllThings.changeDown(newItem, position);
                        alertDialog.dismiss();
                        mMultipleItemAdapter.notifyDataSetChanged();
                    }

                });
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = AllThings.sThingList.get(position).getId();
                        AllThings.removeThing(position);
                        DataSupport.delete(ThingListItem.class, id);
                        alertDialog.dismiss();
                        mMultipleItemAdapter.notifyDataSetChanged();
                    }
                });
                alertDialog.show();
                return false;
            }
        });


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view2 = View.inflate(getContext(), R.layout.input_thing, null);

                final EditText inputDate = (EditText) view2.findViewById(R.id.input_date);
                final EditText inputTime = (EditText) view2.findViewById(R.id.input_time);
                final EditText inputContent = (EditText) view2.findViewById(R.id.input_content);
                final Button done = (Button) view2.findViewById(R.id.down_button);
                final Button cancel = (Button) view2.findViewById(R.id.cancel_button);
                final Button remove = (Button) view2.findViewById(R.id.remove_button);
                remove.setVisibility(View.GONE);

                // 设置参数
                builder//.setTitle("Login")//.setIcon(R.drawable.ic_launcher)
                        .setView(view2);
                // 创建对话框
                final AlertDialog alertDialog = builder.create();
//                final int pos = position;
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] date = inputDate.getText().toString().split("/");
                        String[] time = inputTime.getText().toString().split(":");
                        Date newDate = new Date(Integer.valueOf(date[0]) - 1900,
                                Integer.valueOf(date[1]) - 1, Integer.valueOf(date[2]),
                                Integer.valueOf(time[0]), Integer.valueOf(time[1]));
                        ThingListItem newItem = new ThingListItem(newDate, inputContent.getText().toString(), 1);
                        AllThings.addThing(newItem);
                        newItem.save();
                        alertDialog.dismiss();
                        mMultipleItemAdapter.notifyDataSetChanged();
                    }

                });
                alertDialog.show();
            }
        });

//        mMultipleItemAdapter.setOnLongClickListener(new MultipleItemAdapter.OnItemLongClickListener() {
//            @Override
//            public void onItemLongClick(View view, int position) {
//                mLinearLayout = (LinearLayout) view.findViewById(R.id.button_group);
//                mLinearLayout.setVisibility(View.VISIBLE);
//            }
//        });
        mRecyclerView.setAdapter(mMultipleItemAdapter);
        mMultipleItemAdapter.notifyDataSetChanged();

        return rootView;
    }

    private void initData() {
        AllThings.addThing(new ThingListItem(new Date(2017 - 1900, 10 - 1, 29), "29", 1));
        AllThings.addThing(new ThingListItem(new Date(2017 - 1900, 10 - 1, 28), "28", 1));
        AllThings.addThing(new ThingListItem(new Date(2017 - 1900, 10 - 1, 27), "27", 1));
        AllThings.addThing(new ThingListItem(new Date(2017 - 1900, 10 - 1, 26), "26", 1));
        AllThings.addThing(new ThingListItem(new Date(2017 - 1900, 10 - 1, 27), "27 2", 1));
        AllThings.addThing(new ThingListItem(new Date(2017 - 1900, 10 - 1, 27), "27 3", 1));
        AllThings.addThing(new ThingListItem(new Date(2017 - 1900, 10 - 1, 27), "27 4", 1));
        AllThings.addThing(new ThingListItem(new Date(2017 - 1900, 10 - 1, 29), "29 2", 1));
        AllThings.addThing(new ThingListItem(new Date(2017 - 1900, 10 - 1, 25), "25", 1));
        AllThings.addThing(new ThingListItem(new Date(2017 - 1900, 10 - 1, 29), "29 3", 1));
        AllThings.addThing(new ThingListItem(new Date(2017 - 1900, 10 - 1, 29), "29 4", 1));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



}
