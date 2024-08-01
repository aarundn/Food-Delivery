package com.example.fooddelivery.entities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.fooddelivery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GetStartedActivity extends AppCompatActivity {
    private Button getStartedButton;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        getStartedButton = findViewById(R.id.getStartedButton);
        progressBar = findViewById(R.id.getStartedProgressBar);
        progressBar.setVisibility(View.GONE);
        getStartedButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            getStartedButton.setText("");
            Intent intent = new Intent(GetStartedActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            GetStartedActivity.this.finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null){
            Intent intent  = new Intent(GetStartedActivity.this, MainActivity.class);
            startActivity(intent);
            GetStartedActivity.this.finish();
        }
    }
}