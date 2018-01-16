package com.softvilla.slmparentportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notes extends AppCompatActivity {

    Context context;
    Fragment mFragment;
    public static final String GETRESULT_URL = "http://slmhighschool.com/edusolutions/Api/getNotice";
    ArrayList<NotesInfo> data;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        List<Child> child = Child.listAll(Child.class);
        for(Child obj : child){
            if(obj.childIdentity.equalsIgnoreCase(AttendanceInfo.id)){
                obj.notice=0;
                obj.save();
            }
        }

        //final View view = inflate(R.layout.activity_notes,false);
        data = new ArrayList<NotesInfo>();
        //context = view.getContext();
        getNotes();

        recyclerView = (RecyclerView) findViewById(R.id.notesrv);
        Recyler_Adapter_Notes adapter = new Recyler_Adapter_Notes(data,this);
        recyclerView.setAdapter(adapter);
        //adapter.setOnCardClickListner((Recyler_Adapter_Notes.OnCardClickListner) Notes.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);



        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.customlayout, null);
        v.findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefrences = PreferenceManager.getDefaultSharedPreferences(Notes.this);
                SharedPreferences.Editor editor = prefrences.edit();
                editor.putString("isLogin","0");
                editor.apply();
                finish();
                startActivity(new Intent(Notes.this,MainActivity.class));
            }
        });
        v.findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Notes.this,MainMenu.class));
            }
        });

        TextView label = (TextView) v.findViewById(R.id.Label);
        label.setText("Notes");

        actionBar.setCustomView(v);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void getNotes(){
        final ProgressDialog pDialog = new ProgressDialog(Notes.this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
//                            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                            pDialog.setIndeterminate(true);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GETRESULT_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                NotesInfo obj = new NotesInfo();
                                obj.date = jsonObject1.getString("date" + i);
                                obj.notes = jsonObject1.getString("notice" + i);




                                data.add(obj);

                            }

                            if(data.size() == 0){
                                Toast.makeText(Notes.this,"No Notes",Toast.LENGTH_LONG).show();
                            }
                            Recyler_Adapter_Notes adapter = new Recyler_Adapter_Notes(data, Notes.this);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Notes.this));

                            pDialog.dismiss();
                        } catch (JSONException e) {
                            pDialog.dismiss();
                            // Toast.makeText(LogIn.this,"Json Error",Toast.LENGTH_SHORT).show();

                            //Toast.makeText(LogIn.this,e.toString(),Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Notes.this, error.toString(), Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPreferences.edit();*/
                map.put("id", AttendanceInfo.id);
                return map;
            }

        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(Notes.this);
        requestQueue.add(stringRequest);
    }
}
