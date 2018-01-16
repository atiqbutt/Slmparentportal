package com.softvilla.slmparentportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class Quiz extends Fragment implements Recyler_Adapter_Quiz.OnCardClickListner {

    Context context;
    Fragment mFragment;
    public static final String GETRESULT_URL = "http://slmhighschool.com/edusolutions/Api/getSubjectsResult";
    ArrayList<QuizInfo> data;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.quiz,container,false);
        data = new ArrayList<QuizInfo>();
        context = view.getContext();
        getResult();
        recyclerView = (RecyclerView) view.findViewById(R.id.quizrv);
        Recyler_Adapter_Quiz adapter = new Recyler_Adapter_Quiz(data,view.getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnCardClickListner((Recyler_Adapter_Quiz.OnCardClickListner) Quiz.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        return view;
    }

    @Override
    public void OnCardClicked(View view, int position) {

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


                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                QuizInfo obj = new QuizInfo();
                                obj.name = jsonObject1.getString("subName" + i);
                                obj.obtainMarks = jsonObject1.getString("obtain_marks" + i);
                                obj.totalMarks = jsonObject1.getString("total_marks" + i);
                                obj.date = jsonObject1.getString("paper_date" + i);



                                data.add(obj);

                            }
                            Recyler_Adapter_Quiz adapter = new Recyler_Adapter_Quiz(data, context);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));

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
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                map.put("id", AttendanceInfo.resultId);
                return map;
            }

        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }



//    public List<QuizInfo> fill_with_data () {
//
//        List<QuizInfo> data = new ArrayList<>();
//
//        data.add(new QuizInfo("Mr Rehan"));
//        data.add(new QuizInfo("Mr Baqir"));
//        data.add(new QuizInfo("Mr Salman"));
//
//
//        return data;
//
//
//    }
}
