package com.example.fooddelivery.fragments.loginfragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddelivery.R;
import com.example.fooddelivery.entities.EmailVerificationActivity;
import com.example.fooddelivery.entities.MainActivity;
import com.example.fooddelivery.entities.ResetPasswordActivity;
import com.example.fooddelivery.viewmodel.UserViewModel;

public class SignInFragment extends Fragment {
    private UserViewModel userViewModel;
    private Button loginButton;
    private EditText emailEdt, passwordEdt;
    private ProgressBar loginProgressBar;
    private TextView forgotPasswordTxt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        emailEdt = view.findViewById(R.id.loginEmailEdt);
        passwordEdt = view.findViewById(R.id.loginPassEdt);
        loginProgressBar = view.findViewById(R.id.loginProgressBar);
        forgotPasswordTxt = view.findViewById(R.id.forgotPassText);
        loginButton = view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            loginProgressBar.setVisibility(View.VISIBLE);
            loginButton.setText("");
            loadingInputsState(emailEdt);
            loadingInputsState(passwordEdt);
            if (inputsCheck()){
                userViewModel.login(emailEdt.getText().toString().trim(), passwordEdt.getText().toString().trim());
            } else {
                loginProgressBar.setVisibility(View.GONE);
                loginButton.setText("Login");
                normalInputsState(emailEdt);
                normalInputsState(passwordEdt);
            }
        });

        userViewModel.getIsLogged().observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoggedIn) {
                if (isLoggedIn){
                    Intent intent = new Intent(requireActivity(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    Toast.makeText(requireActivity()
                            , "Failed to sign in. Try again.", Toast.LENGTH_SHORT).show();
                    loginProgressBar.setVisibility(View.GONE);
                    loginButton.setText("Login");
                    normalInputsState(emailEdt);
                    normalInputsState(passwordEdt);
                }
            }
        });

        forgotPasswordTxt.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), ResetPasswordActivity.class);
            startActivity(intent);
        });
    }
    private Boolean inputsCheck() {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEdt.getText().toString()).matches()) {
            emailEdt.setError("please enter valid email format!!");
            return false;
        } else if (emailEdt.getText().toString().isEmpty()
                || passwordEdt.getText().toString().isEmpty()) {
            Toast.makeText(requireActivity(), "inputs must not be Empty!!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordEdt.getText().toString().length() < 6) {
            passwordEdt.setError("password must be up to 6 digits ");
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