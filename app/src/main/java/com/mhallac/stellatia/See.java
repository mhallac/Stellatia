package com.mhallac.stellatia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class See extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String value = intent.getStringExtra("key");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see);
    }
}
