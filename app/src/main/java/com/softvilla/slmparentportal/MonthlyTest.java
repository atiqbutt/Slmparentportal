package com.softvilla.slmparentportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Map;

/**
 * Created by Malik on 03/08/2017.
 */

public class MonthlyTest extends Fragment implements Recycler_Adapter_Monthly_Test.OnCardClickListner {

    Context context;
    Fragment mFragment;
    public static final String GETRESULT_URL = "http://slmhighschool.com/edusolutions/Api/getResult";
    ArrayList<MonthlyTestInfo> data;
    RecyclerView recyclerView;
    FragmentTransaction transaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.monthlytest, container, false);
        data = new ArrayList<MonthlyTestInfo>();
        context = view.getContext();
        getResult();
        //List<MonthlyTestInfo> data = fill_with_data();
        transaction = getFragmentManager().beginTransaction();
        recyclerView = (RecyclerView) view.findViewById(R.id.monthlytestrv);
        Recycler_Adapter_Monthly_Test adapter = new Recycler_Adapter_Monthly_Test(data, view.getContext(),transaction);
        recyclerView.setAdapter(adapter);
        adapter.setOnCardClickListner(MonthlyTest.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        return view;


    }

    @Override
    public void OnCardClicked(View view, int position) {
        /*AttendanceInfo.resultId = data.get(position).resultId;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.monthtestfragment, new Quiz());
        //transaction.addToBackStack(null);
        transaction.commit();*/
        //Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show();
    }


    public void getResult(){
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
//                            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                            pDialog.setIndeterminate(true);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GETRESULT_URL,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {


                        Boolean isFee = false;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = jsonArray.length() - 1; i >= 0 ; i--) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                MonthlyTestInfo obj = new MonthlyTestInfo();
                                obj.name = jsonObject1.getString("examType" + i);
                                obj.obttMarks = jsonObject1.getString("obtain_marks" + i);
                                obj.totalMarks = jsonObject1.getString("total_marks" + i);
                                obj.psition = jsonObject1.getString("position" + i);
                                obj.resultId = jsonObject1.getString("id" + i);
                                data.add(obj);
                                isFee = true;

                            }
                            Recycler_Adapter_Monthly_Test adapter = new Recycler_Adapter_Monthly_Test(data, context,transaction);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));

                            pDialog.dismiss();
                            if(!isFee){
                                Toast.makeText(context,"No Data Available",Toast.LENGTH_LONG).show();
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
                        Toast.makeText(context, "Connection Failed", Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                map.put("id", AttendanceInfo.promId);
                return map;
            }

        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }



}

//    public List<MonthlyTestInfo> fill_with_data () {
//
//        List<MonthlyTestInfo> data = new ArrayList<>();
//
//        data.add(new MonthlyTestInfo("Prof. Dr. Tanvir Ahmed"));
//        data.add(new MonthlyTestInfo("Bilal Ahmed"));
//        data.add(new MonthlyTestInfo("Fahad Najeeb"));
//        data.add(new MonthlyTestInfo("Named Ch."));
//        data.add(new MonthlyTestInfo("Junaid Tahir"));
//        data.add(new MonthlyTestInfo("Hidayat ur Rahman"));
//        data.add(new MonthlyTestInfo("Sadiq Ameen"));
//        data.add(new MonthlyTestInfo("Zain Siddique"));
//        data.add(new MonthlyTestInfo("Saim Asghar"));
//        data.add(new MonthlyTestInfo("Rana Mohtasham Aftab"));
//        data.add(new MonthlyTestInfo("Maria Bashir"));
//        data.add(new MonthlyTestInfo("Maria Khan"));
//        data.add(new MonthlyTestInfo("Amjad Khan"));
//
//        return data;
//
//
//    }


