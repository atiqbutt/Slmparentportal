package com.softvilla.slmparentportal;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.List;

public class Attendance extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarattendance);
        setSupportActionBar(toolbar);
        List<Child> child = Child.listAll(Child.class);
        for(Child obj : child){
            if(obj.childIdentity.equalsIgnoreCase(AttendanceInfo.id)){
                obj.attendance=0;
                obj.save();
            }
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_attendance);
        tabLayout.addTab(tabLayout.newTab().setText("Current Month"));
        tabLayout.addTab(tabLayout.newTab().setText("Previous Month"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pagerattandance);
        final PagerAdapter adapter = new PageAdapterChart(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}
