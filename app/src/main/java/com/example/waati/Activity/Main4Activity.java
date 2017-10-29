package com.example.waati.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.waati.Adapter.FragmentPageAdapter;
import com.example.waati.Bean.AllThings;
import com.example.waati.Bean.ThingListItem;
import com.example.waati.Fragment.ThingFragment;
import com.example.waati.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main4Activity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private List<String> titleList = Arrays.asList("待办");//, "已办");

    private ThingFragment mThingFragment1;
//    private ThingFragment mThingFragment2;
    private FragmentPagerAdapter mAdapter;
    private static final String TAG = "Main4Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main4);

        List<ThingListItem> thingList = DataSupport.findAll(ThingListItem.class);
        for (ThingListItem t : thingList) {
            AllThings.addThing(t);
            Log.d(TAG, "onCreate: " + "数据库加载数据");
        }

        initView();
    }


    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.thing_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.thing_tab);
        mThingFragment1 = new ThingFragment();
//        mThingFragment2 = new ThingFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(mThingFragment1);
//        fragmentList.add(mThingFragment2);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
//        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));

        mAdapter = new FragmentPageAdapter(getSupportFragmentManager(), fragmentList, titleList);

        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
