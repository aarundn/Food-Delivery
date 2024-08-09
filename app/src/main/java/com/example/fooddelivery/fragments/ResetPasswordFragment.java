package com.example.fooddelivery.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fooddelivery.R;
import com.example.fooddelivery.entities.LoginActivity;
import com.example.fooddelivery.viewmodel.UserViewModel;


public class ResetPasswordFragment extends Fragment {


    private EditText emailEdt;
    private Button sendRestButton, backButton;
    private ProgressBar resetProgressBar;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        sendRestButton = view.findViewById(R.id.resetPassButton);
        emailEdt = view.findViewById(R.id.resetPassEmailEdt);
        resetProgressBar = view.findViewById(R.id.resetProgressBar);
        backButton = view.findViewById(R.id.resetBackButton);

        backButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_resetPasswordFragment_to_loginFragment);
        });

        userViewModel.getIsResetPasswordSent().observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isResetPasswordSent) {
                if (isResetPasswordSent){
                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    Toast.makeText(requireActivity(),
                            "Password reset link was sent!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    requireActivity().finish();
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
                Toast.makeText(requireActivity(),
                        "your Password reset Failed!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private Boolean inputsCheck() {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEdt.getText().toString()).matches()) {
            emailEdt.setError("please enter valid email format!!");
            return false;
        } else if (emailEdt.getText().toString().isEmpty()) {
            Toast.makeText(requireActivity(), "inputs must not be Empty!!", Toast.LENGTH_SHORT).show();
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