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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.fooddelivery.databinding.FragmentProfileBinding;
import com.example.fooddelivery.models.User;
import com.example.fooddelivery.viewmodel.ProfileViewModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.concurrent.atomic.AtomicReference;


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
        User user = new User();
        profileViewModel.getGetUserInfo().observe(requireActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {

                binding.userNameProfile.setText(user.getUserName());
                binding.userEmailProfile.setText(user.getEmailAddress());
                binding.userNumberProfile.setText(user.getPhoneNumber());
                binding.userAddressProfile.setText(user.getAddress());
                Glide.with(requireActivity()).load(user.getImagePath()).into(binding.profileImage);
                user = new User(user.getId(), user.getEmailAddress(), user.getPassword(),
                        user.getImagePath(), user.getPhoneNumber(), user.getUserName(), user.getAddress());
                inputsState(false, binding.userNameProfile);
                inputsState(false, binding.userNumberProfile);
                inputsState(false, binding.userAddressProfile);
            }
        });
        binding.changeText.setOnClickListener(v -> {
            inputsState(true, binding.userNameProfile);
            inputsState(true, binding.userNumberProfile);
            inputsState(true, binding.userAddressProfile);
            binding.changeText.setText("save");
            binding.changeText.setOnClickListener(v1 -> {
                if (inputsCheck()){
                    profileViewModel.modifyUserInfo(user);
                    profileViewModel.getIsUserAdded().observe(requireActivity(), isAdded -> {
                        if (isAdded){
                            binding.changeText.setText("Change");
                            inputsState(false, binding.userNameProfile);
                            inputsState(false, binding.userNumberProfile);
                            inputsState(false, binding.userAddressProfile);
                        }
                    });
                }
            });

        });
    }

    private void inputsState(boolean b, EditText editText) {
        if (b){
           editText.setFocusable(true);
           editText.setFocusableInTouchMode(true);
        } else {
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
        }
    }

    private Boolean inputsCheck() {
        if (binding.userNameProfile.getText().toString().isEmpty()) {
            binding.userNameProfile.setError("username cannot be empty!");
            return false;
        } else if (binding.userNumberProfile.getText().toString().isEmpty()) {
            binding.userNumberProfile.setError("number cannot be Empty !");
            return false;
        } else if (binding.userAddressProfile.getText().toString().isEmpty()) {
            binding.userAddressProfile.setError("password must be up to 6 digits ");
            return false;
        }
        return true;
    }

}