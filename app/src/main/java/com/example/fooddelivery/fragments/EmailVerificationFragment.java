package com.example.fooddelivery.fragments;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fooddelivery.R;
import com.example.fooddelivery.entities.MainActivity;
import com.example.fooddelivery.fragments.loginfragments.SignUpFragmentArgs;
import com.example.fooddelivery.viewmodel.UserViewModel;

public class EmailVerificationFragment extends Fragment {

    private UserViewModel userViewModel;
    private ProgressBar verifyProgress;
    private Button verifyButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_verification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        verifyProgress = view.findViewById(R.id.verificationProgressBar);
        verifyButton = view.findViewById(R.id.verificationButton);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        assert getArguments() != null;
        String email = getArguments().getString("email");
        String password = getArguments().getString("password");
        Log.d("NAME", email +" "+ password);

        verifyButton.setOnClickListener(v -> {
            verifyProgress.setVisibility(View.VISIBLE);
            verifyButton.setText("");
            userViewModel.checkEmailVerification(email, password);

        });

        userViewModel.getIsSignedIn().observe(requireActivity(), isSignedIn -> {

            if (isSignedIn) {
                verifyProgress.setVisibility(View.GONE);
                verifyButton.setText("verify");
                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            } else {
                Toast.makeText(requireActivity(), "Failed to sign in. Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}