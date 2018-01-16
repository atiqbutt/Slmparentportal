package com.softvilla.slmparentportal;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {
    SharedPreferences preferences;
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();

        registerToken(token);
    }

    private void registerToken(String token) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("token",token)
                .add("cnic",preferences.getString("cnic",""))
                .build();

        Request request = new Request.Builder()
                .url("http://slmhighschool.com/edusolutions/Api/updateToken")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
