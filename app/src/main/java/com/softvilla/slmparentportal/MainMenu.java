package com.softvilla.slmparentportal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

public class MainMenu extends AppCompatActivity {





    GridView grid;
    Grid adapter;
    int[] text;
//    String[] web = {
//            "Attendance",
//            "Fee",
//            "Result",
//            "Notes",
//
//
//    } ;
    Integer[] imageid = {
            R.drawable.attendance,
            R.drawable.fee,
            R.drawable.results,
            R.drawable.notes,

    };

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        AttendanceInfo.whichFragment = "abc";
        text = new int[4];

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.customlayout, null);
        v.findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefrences = PreferenceManager.getDefaultSharedPreferences(MainMenu.this);
                SharedPreferences.Editor editor = prefrences.edit();
                editor.putString("isLogin","0");
                editor.apply();
                finish();
                startActivity(new Intent(MainMenu.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
        v.findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainMenu.this,Children.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });

        TextView label = (TextView) v.findViewById(R.id.Label);
        label.setText("Main Menu");

        actionBar.setCustomView(v);

        List<Child> child = Child.listAll(Child.class);
        for(Child obj : child){
            if(obj.childIdentity.equalsIgnoreCase(AttendanceInfo.id)){
                text[0] = obj.attendance;
                text[1] = obj.fee;
                text[2] = obj.result;
                text[3] = obj.notice;
            }
        }
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        adapter = new Grid(MainMenu.this, imageid,text);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //  String poss = String.valueOf(parent.getItemAtPosition(position));

                if(position == 0){
                    Intent intent = new Intent(MainMenu.this, Attendance.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
                if(position == 1){
                    Intent intent = new Intent(MainMenu.this, Fee.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
                if(position == 2){
                    Intent intent = new Intent(MainMenu.this, Results.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
                if(position == 3){
                    Intent intent = new Intent(MainMenu.this, Notes.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }


                // Toast.makeText(Main2Activity.class, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });


//        Bundle extras = getIntent().getExtras();
//        if(extras != null){
//            id = extras.getString("id");
//        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainMenu.this,Children.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }






    //    public void Results(View view) {
//        startActivity(new Intent(this,Results.class));
//    }
//
//    public void Fee(View view) {
//        startActivity(new Intent(this,Fee.class));
//    }
//
//    public void Attendacne(View view) {
//        startActivity(new Intent(this,Attendance.class));
//    }
//
//    public void Notes(View view) {startActivity(new Intent(this,Notes.class));}
}
