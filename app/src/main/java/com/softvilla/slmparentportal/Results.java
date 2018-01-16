package com.softvilla.slmparentportal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        List<Child> child = Child.listAll(Child.class);
        for(Child obj : child){
            if(obj.childIdentity.equalsIgnoreCase(AttendanceInfo.id)){
                obj.result=0;
                obj.save();
            }
        }


        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.customlayout, null);
        v.findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefrences = PreferenceManager.getDefaultSharedPreferences(Results.this);
                SharedPreferences.Editor editor = prefrences.edit();
                editor.putString("isLogin","0");
                editor.apply();
                finish();
                startActivity(new Intent(Results.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
        v.findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(AttendanceInfo.whichFragment.equalsIgnoreCase("Subject")){
                    AttendanceInfo.whichFragment = "OverAll";
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    //transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    transaction.replace(R.id.monthtestfragment, new MonthlyTest());
                    //transaction.addToBackStack(null);
                    transaction.commit();
                }
                else {
                    startActivity(new Intent(Results.this,MainMenu.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }

            }
        });

        TextView label = (TextView) v.findViewById(R.id.Label);
        label.setText("Result");

        actionBar.setCustomView(v);




    }

    @Override
    public void onBackPressed() {
        if(AttendanceInfo.whichFragment.equalsIgnoreCase("Subject")){
            AttendanceInfo.whichFragment = "OverAll";
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            transaction.replace(R.id.monthtestfragment, new MonthlyTest());
            //transaction.addToBackStack(null);
            transaction.commit();
        }
        else {
            startActivity(new Intent(Results.this,MainMenu.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }

    //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        tabLayout.addTab(tabLayout.newTab().setText("Monthly Test"));
//        tabLayout.addTab(tabLayout.newTab().setText("Quiz"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        final PagerAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//

    }

