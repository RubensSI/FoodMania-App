 package com.app.foodmaniaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.foodmaniaapp.R;
import com.google.firebase.auth.FirebaseAuth;

 public class HomeActivity extends AppCompatActivity {

     private FirebaseAuth mAuth;
     private Button btn_logout_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        btn_logout_home = findViewById(R.id.btn_logout_home);

        btn_logout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                Intent intent = new Intent(HomeActivity.this, AutenticationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}