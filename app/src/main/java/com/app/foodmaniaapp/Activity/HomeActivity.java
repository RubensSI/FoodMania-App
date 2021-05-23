 package com.app.foodmaniaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.foodmaniaapp.Helper.FirebaseConfig;
import com.app.foodmaniaapp.R;
import com.google.firebase.auth.FirebaseAuth;

 public class HomeActivity extends AppCompatActivity {

     private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseConfig.getFirefebaseAutentication();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Food Mania");
        setSupportActionBar(toolbar);

    }
}