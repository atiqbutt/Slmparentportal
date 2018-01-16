package com.softvilla.slmparentportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Fee extends AppCompatActivity implements Recycler_Adapter_Fee.OnCardClickListner {
    //

//    ExpandableRelativeLayout expandLayout;
    ArrayList<FeeInfo> data;
    RecyclerView recyclerView;
    TextView status;
    public static final String Fee_URL = "http://slmhighschool.com/edusolutions/Api/getFee";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        List<Child> child = Child.listAll(Child.class);
        for(Child obj : child){
            if(obj.childIdentity.equalsIgnoreCase(AttendanceInfo.id)){
                obj.fee=0;
                obj.save();
            }
        }


        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.customlayout, null);
        v.findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefrences = PreferenceManager.getDefaultSharedPreferences(Fee.this);
                SharedPreferences.Editor editor = prefrences.edit();
                editor.putString("isLogin","0");
                editor.apply();
                finish();
                startActivity(new Intent(Fee.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
        v.findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Fee.this,MainMenu.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });

        TextView label = (TextView) v.findViewById(R.id.Label);
        label.setText("Fee");

        actionBar.setCustomView(v);


        data = new ArrayList<FeeInfo>();
        status = (TextView) findViewById(R.id.CurrentStatus);

        recyclerView = (RecyclerView) findViewById(R.id.feerv);
        Recycler_Adapter_Fee adapter = new Recycler_Adapter_Fee(data, this);
        recyclerView.setAdapter(adapter);
        adapter.setOnCardClickListner(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

        getFee();

}

//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.lbs:
//                expandLayout = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);
//                expandLayout.toggle();
//
//        }
//    }

    @Override
    public void OnCardClicked(View view, int position) {

////        if(position==0){
////            Intent c1 = new Intent(Fee.this, FeeDetail.class);
////            c1.putExtra("description", "Hassan");
////            c1.putExtra("descriptionclass", "10th");
////            c1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////            startActivity(c1);
//        }


    }




    public void getFee(){
        final ProgressDialog pDialog = new ProgressDialog(Fee.this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
//                            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                            pDialog.setIndeterminate(true);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Fee_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {


                        Boolean isFee = false;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = jsonArray.length() - 1; i >= 0; i--) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                FeeInfo obj = new FeeInfo();
                                obj.date = jsonObject1.getString("submitted_at" + i);
                                obj.amount = jsonObject1.getString("feeAmount" + i);
                                obj.received = jsonObject1.getString("recieved" + i);
                                obj.remaining = jsonObject1.getString("remaining" + i);
                                obj.advance = jsonObject1.getString("advance" + i);
                                obj.issueDate = jsonObject1.getString("date" + i);
                                obj.rem = jsonObject1.getString("rem" + i);
                                obj.expireDate = jsonObject1.getString("date_expire" + i);
                                obj.Total = jsonObject1.getString("rem"+i);
                                try {
                                    obj.month = getMonth(obj.issueDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                isFee = true;
                                data.add(obj);

                                if(i == jsonArray.length() - 1){
                                    Double rem = Double.parseDouble(jsonObject1.getString("rem" + i)) - Double.parseDouble(jsonObject1.getString("recieved" + i));
                                    status.setText("Remaining Balance: " + String.valueOf(rem) + "PKR");
                                }

                            }
                            Recycler_Adapter_Fee adapter = new Recycler_Adapter_Fee(data, Fee.this);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Fee.this));

                            pDialog.dismiss();
                            if(!isFee){
                                Toast.makeText(Fee.this,"No Data Available",Toast.LENGTH_LONG).show();
                            }
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
                        Toast.makeText(Fee.this, "Connection Failed", Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put("id", AttendanceInfo.promId);
                return map;
            }

        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(Fee.this);
        requestQueue.add(stringRequest);
    }

    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }

}