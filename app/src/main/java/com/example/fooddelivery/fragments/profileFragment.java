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

import com.example.fooddelivery.R;
import com.example.fooddelivery.databinding.FragmentProfileBinding;
import com.makeramen.roundedimageview.RoundedImageView;


public class profileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private EditText userNameEdt, addressEdt, numberEdt;
    private RoundedImageView profileImagePath;
    private TextView changeText;
    private FrameLayout updateLayoutBtn;
    private LinearLayout orderLayout, pendingLayout,faqLayout, helpLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(LayoutInflater.from(requireActivity()));
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}