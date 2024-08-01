package com.example.fooddelivery.fragments.loginfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fooddelivery.R;
import com.example.fooddelivery.viewmodel.UserViewModel;

public class SignInFragment extends Fragment {
    private UserViewModel userViewModel;
    private EditText emailEdt, passwordEdt;
    private Button loginBtn;
    private TextView forgetPassword;
    private ProgressBar loginProgressBar;
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
        loginBtn = view.findViewById(R.id.loginButton);
        forgetPassword = view.findViewById(R.id.forgotPassText);
        loginProgressBar = view.findViewById(R.id.loginProgressBar);


    }
}