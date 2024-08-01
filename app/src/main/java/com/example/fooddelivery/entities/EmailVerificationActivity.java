package com.example.fooddelivery.entities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fooddelivery.R;
import com.example.fooddelivery.viewmodel.UserViewModel;

public class EmailVerificationActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    private ProgressBar verifyProgress;
    private Button verifyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        verifyProgress = findViewById(R.id.verificationProgressBar);
        verifyButton = findViewById(R.id.verificationButton);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        Log.d("NAME", email +" "+ password);

//        userViewModel.getIsSignedIn().observe(this, isSignedIn -> {
//            verifyProgress.setVisibility(View.GONE);
//            verifyButton.setText("verify");
//            if (isSignedIn) {
//                Intent intent = new Intent(EmailVerificationActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            } else {
//                Toast.makeText(this, "Failed to sign in. Try again.", Toast.LENGTH_SHORT).show();
//            }
//        });

        verifyButton.setOnClickListener(v -> {
            verifyProgress.setVisibility(View.VISIBLE);
            verifyButton.setText("");

            userViewModel.getIsSignedIn().observe(this, isSignedIn -> {
                if (isSignedIn) {
                    verifyProgress.setVisibility(View.GONE);
                    verifyButton.setText("verify");
                    Intent intent = new Intent(EmailVerificationActivity.this, MainActivity.class);
                    startActivity(intent);
                    EmailVerificationActivity.this.finish();
                } else {
                    Toast.makeText(this, "Failed to sign in. Try again.", Toast.LENGTH_SHORT).show();
                }
            });

        });



    }
}