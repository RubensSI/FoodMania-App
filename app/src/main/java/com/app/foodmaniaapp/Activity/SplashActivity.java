package com.app.foodmaniaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.foodmaniaapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                OpenAutentication();
            }
        }, 3000);
    }

    private void OpenAutentication() {
        Intent i = new Intent(SplashActivity.this, AutenticationActivity.class);
        startActivity(i);
        finish();
    }


}