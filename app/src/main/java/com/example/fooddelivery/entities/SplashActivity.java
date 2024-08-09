package com.example.fooddelivery.entities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.fooddelivery.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, 4000);
    }
}