package com.example.fooddelivery.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fooddelivery.R;
import com.example.fooddelivery.databinding.FragmentProfileBinding;
import com.example.fooddelivery.viewmodel.ProfileViewModel;
import com.makeramen.roundedimageview.RoundedImageView;


public class profileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private EditText userNameEdt, addressEdt, numberEdt;
    private RoundedImageView profileImagePath;
    private TextView changeText;
    private FrameLayout updateLayoutBtn;
    private LinearLayout orderLayout, pendingLayout,faqLayout, helpLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        profileViewModel.getGetUserInfo().observe(requireActivity(), userInfo -> {
            binding.userNameProfile.setText("Haroun");
            binding.userEmailProfile.setText(userInfo.getEmailAddress());
        });
    }
}