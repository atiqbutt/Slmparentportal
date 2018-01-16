package com.softvilla.slmparentportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String LOGIN_URL = "http://slmhighschool.com/edusolutions/Api/login";
    private EditText editTextUsername;
    public static final String KEY_USERNAME="username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        editTextUsername = (EditText) findViewById(R.id.username);
    }

    private void userLogin() {
        final String username = editTextUsername.getText().toString();



        boolean isEmpty=false;
        if(TextUtils.isEmpty(editTextUsername.getText().toString())) {
            editTextUsername.setError("Enter Your CNIC");
            isEmpty = true;
        }

        if(!isEmpty){

            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Signing in...");
            pDialog.setCancelable(false);
//                            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                            pDialog.setIndeterminate(true);
            pDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            try{
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String message = jsonObject.getString("msg");

                                if(message.equalsIgnoreCase("false")){
                                    Toast.makeText(MainActivity.this,"Incorect CNIC.",Toast.LENGTH_SHORT).show();
                                }

                                else if(message.equalsIgnoreCase("true")) {
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("cnic",username);
                                    editor.putString("isLogin","1");
                                    editor.apply();
                                    finish();
                                    startActivity(new Intent(MainActivity.this,Children.class));
                                }
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
                            Toast.makeText(MainActivity.this,"Connection Failed", Toast.LENGTH_LONG ).show();
                            pDialog.hide();
                        }
                    }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("cnic",username);
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

    public void ChildrenDetail(View view) {
        userLogin();
    }



}
