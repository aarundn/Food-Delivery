package com.example.fooddelivery.entities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fooddelivery.R;
import com.example.fooddelivery.viewmodel.UserViewModel;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText emailEdt;
    private Button sendRestButton, backButton;
    private ProgressBar resetProgressBar;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        sendRestButton = findViewById(R.id.resetPassButton);
        emailEdt = findViewById(R.id.resetPassEmailEdt);
        resetProgressBar = findViewById(R.id.resetProgressBar);
        backButton = findViewById(R.id.resetBackButton);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            ResetPasswordActivity.this.finish();
        });

        userViewModel.getIsResetPasswordSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isResetPasswordSent) {
                if (isResetPasswordSent){
                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    Toast.makeText(ResetPasswordActivity.this,
                            "Password reset link was sent!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    ResetPasswordActivity.this.finish();
                }
            }
        });
        sendRestButton.setOnClickListener(v ->  {
            resetProgressBar.setVisibility(View.VISIBLE);
            sendRestButton.setText("");
            loadingInputsState(emailEdt);
            if (inputsCheck()){
                userViewModel.forgotPassword(emailEdt.getText().toString().trim());
            } else {
                resetProgressBar.setVisibility(View.GONE);
                sendRestButton.setText("send");
                normalInputsState(emailEdt);
                Toast.makeText(ResetPasswordActivity.this,
                        "your Password reset Failed!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private Boolean inputsCheck() {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEdt.getText().toString()).matches()) {
            emailEdt.setError("please enter valid email format!!");
            return false;
        } else if (emailEdt.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "inputs must not be Empty!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loadingInputsState(EditText inputs){
        inputs.setTextColor(getResources().getColor(R.color.secondItemsAndIcons));
        inputs.setFocusable(false);
        inputs.setFocusableInTouchMode(false);;
    }
    private void normalInputsState(EditText inputs){
        inputs.setTextColor(getResources().getColor(R.color.black));
        inputs.setFocusable(true);
        inputs.setFocusableInTouchMode(true);;
    }
}