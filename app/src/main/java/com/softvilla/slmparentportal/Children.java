package com.softvilla.slmparentportal;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Children extends AppCompatActivity implements Recycler_View_Adapter.OnCardClickListner {

    public static final String LOGIN_URL = "http://slmhighschool.com/edusolutions/Api/getStudents";
    public static final String NOTIFICATION_URL = "http://slmhighschool.com/edusolutions/Api/updateToken";
    List<ChildrenInfo> data;
    int Size = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children);
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        UpdateToken(preferences.getString("cnic",""),FirebaseInstanceId.getInstance().getToken());
       // getSupportActionBar().setDisplayShowHomeEnabled(true);


        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        data = new ArrayList<ChildrenInfo>();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        Recycler_View_Adapter adapter = new Recycler_View_Adapter(data, this);
        recyclerView.setAdapter(adapter);
        adapter.setOnCardClickListner(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        userLogin();

    }

    @Override
    public void OnCardClicked(View view, int position) {
        if (position == 0) {
            startActivity(new Intent(this, MainMenu.class));
        }
        if (position == 1) {
            startActivity(new Intent(this, MainMenu.class));
        }
        if (position == 2) {
            startActivity(new Intent(this, MainMenu.class));
        }
        if (position == 3) {
            startActivity(new Intent(this, MainMenu.class));
        }
        if (position == 4) {
            startActivity(new Intent(this, MainMenu.class));
        }
        if (position == 5) {
            startActivity(new Intent(this, MainMenu.class));
        }
        if (position == 6) {
            startActivity(new Intent(this, MainMenu.class));
        }
        if (position == 7) {
            startActivity(new Intent(this, MainMenu.class));
        }
        if (position == 8) {
            startActivity(new Intent(this, MainMenu.class));
        }
        if (position == 9) {
            startActivity(new Intent(this, MainMenu.class));
        }
        if (position == 10) {
            startActivity(new Intent(this, MainMenu.class));
        }

        if (position == 11) {
            startActivity(new Intent(this, MainMenu.class));
        }

        if (position == 12) {
            startActivity(new Intent(this, MainMenu.class));

        }
        Toast.makeText(this,"You Clicked at Row "+position,Toast.LENGTH_SHORT).show();

    }





                    public List<ChildrenInfo> fill_with_data () {

                        List<ChildrenInfo> data = new ArrayList<>();

                        data.add(new ChildrenInfo("Prof. Dr. Tanvir Ahmed", R.drawable.profileimg));
                        data.add(new ChildrenInfo("Bilal Ahmed", R.drawable.profileimg));
                        data.add(new ChildrenInfo("Fahad Najeeb", R.drawable.profileimg));
                        data.add(new ChildrenInfo("Named Ch.", R.drawable.profileimg));
                        data.add(new ChildrenInfo("Junaid Tahir", R.drawable.profileimg));
                        data.add(new ChildrenInfo("Hidayat ur Rahman", R.drawable.profileimg));
                        data.add(new ChildrenInfo("Sadiq Ameen", R.drawable.profileimg));
                        data.add(new ChildrenInfo("Zain Siddique", R.drawable.profileimg));
                        data.add(new ChildrenInfo("Saim Asghar", R.drawable.profileimg));
                        data.add(new ChildrenInfo("Rana Mohtasham Aftab", R.drawable.profileimg));
                        data.add(new ChildrenInfo("Maria Bashir", R.drawable.profileimg));
                        data.add(new ChildrenInfo("Maria Khan", R.drawable.profileimg));
                        data.add(new ChildrenInfo("Amjad Khan", R.drawable.profileimg));

                        return data;


                    }

    private void userLogin() {


        if(true){

            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
//                            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                            pDialog.setIndeterminate(true);
            pDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            List<Child> child = Child.listAll(Child.class);
                            try{
                                JSONArray jsonArray = new JSONArray(response);
                                for(int i = 0; i<jsonArray.length();i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    ChildrenInfo obj = new ChildrenInfo();
                                    obj.userName = jsonObject1.getString("name"+i);
                                    obj.id = jsonObject1.getString("id"+i);
                                    obj.img = jsonObject1.getString("img"+i);
                                    obj.promId = jsonObject1.getString("promid"+i);

                                    for(Child obj1 : child){
                                        if(obj1.childIdentity.equalsIgnoreCase(obj.id)){
                                            obj.text = obj1.attendance + obj1.result + obj1.notice + obj1.fee;
                                        }
                                    }
                                    data.add(obj);
                                    Size++;
                                }
                                if(child.size() != Size){
                                    Child.deleteAll(Child.class);
                                    for(ChildrenInfo obj : data){
                                        Child child1 = new Child();
                                        child1.childIdentity = obj.id;
                                        child1.save();
                                    }
                                }
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
                                Recycler_View_Adapter adapter = new Recycler_View_Adapter(data, Children.this);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(Children.this));
                                pDialog.dismiss();
                            }catch (JSONException e){
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
                            Toast.makeText(Children.this,"Connection Failed", Toast.LENGTH_LONG ).show();
                            pDialog.hide();
                        }
                    }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Children.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    map.put("cnic",sharedPreferences.getString("cnic",""));
                    return map;
                }

            };

            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }



    }

    public void UpdateToken(final String cnic, final String token){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, NOTIFICATION_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Children.this,"Connection Failed", Toast.LENGTH_LONG ).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("cnic",cnic);
                map.put("token",token);
                return map;
            }

        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(startMain);
    }
}
