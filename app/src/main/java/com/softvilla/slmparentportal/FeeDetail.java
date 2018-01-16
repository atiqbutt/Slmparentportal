package com.softvilla.slmparentportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class FeeDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_detail);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        String description = intent.getStringExtra("description");
        ((TextView) findViewById(R.id.description)).setText(description);
        String descriptionclass = getIntent().getStringExtra("descriptionclass");
        ((TextView) findViewById(R.id.descriptionclass)).setText(descriptionclass);




    }
}
