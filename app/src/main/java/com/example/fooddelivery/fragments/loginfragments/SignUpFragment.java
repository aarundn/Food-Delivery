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
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.example.fooddelivery.R;
import com.example.fooddelivery.entities.EmailVerificationActivity;
import com.example.fooddelivery.entities.MainActivity;
import com.example.fooddelivery.models.User;
import com.example.fooddelivery.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;


public class SignUpFragment extends Fragment {
    private EditText emailEdt, passwordEdt, confirmPass;
    private Button registerBtn;
    private ProgressBar registerProgressBar;
    private UserViewModel userViewModel;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailEdt = view.findViewById(R.id.registerEmailEdt);
        passwordEdt = view.findViewById(R.id.registerPassEdt);
        confirmPass = view.findViewById(R.id.confirmPassEdt);
        registerBtn = view.findViewById(R.id.registerButton);
        registerProgressBar = view.findViewById(R.id.registerProgressBar);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        registerBtn.setOnClickListener(v -> {
            if (inputsCheck()) {
                registerProgressBar.setVisibility(View.VISIBLE);
                registerBtn.setText("");
                loadingInputsState(emailEdt);
                loadingInputsState(passwordEdt);
                loadingInputsState(confirmPass);
                userViewModel.SignUpWithEmailAndPassword(emailEdt.getText().toString().trim(), passwordEdt.getText().toString());


            } else {
                registerProgressBar.setVisibility(View.GONE);
                registerBtn.setText("register");
            }
        });

        userViewModel.getIsSignUpSuccessfully().observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                            registerProgressBar.setVisibility(View.GONE);
                            registerBtn.setText("register");
                            Intent intent = new Intent(requireActivity(), EmailVerificationActivity.class);
                            intent.putExtra("email",emailEdt.getText().toString());
                            intent.putExtra("password",passwordEdt.getText().toString());
                            startActivity(intent);
                            requireActivity().finish();
                }
            }
        });
        userViewModel.getIsEmailAlreadyInUse().observe(requireActivity(), emailInUse -> {
            if (emailInUse) {
                registerProgressBar.setVisibility(View.GONE);
                registerBtn.setText("Register");
                normalInputsState(emailEdt);
                normalInputsState(passwordEdt);
                normalInputsState(confirmPass);
                Toast.makeText(requireActivity(), "Email already in use. Please use a different email.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Boolean inputsCheck() {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEdt.getText().toString()).matches()) {
            emailEdt.setError("please enter valid email format!!");
            return false;
        } else if (emailEdt.getText().toString().isEmpty()
                || passwordEdt.getText().toString().isEmpty()
                || confirmPass.getText().toString().isEmpty()) {
            Toast.makeText(requireActivity(), "inputs must not be Empty!!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!(confirmPass.getText().toString()).equals(passwordEdt.getText().toString())) {
            confirmPass.setError("password must ne the same");
            passwordEdt.setError("password must ne the same");
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