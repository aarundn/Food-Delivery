package com.example.fooddelivery.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.fooddelivery.R;
import com.example.fooddelivery.databinding.FragmentProfileBinding;
import com.example.fooddelivery.models.User;
import com.example.fooddelivery.viewmodel.ProfileViewModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


public class profileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private EditText userNameEdt, addressEdt, numberEdt;
    private RoundedImageView profileImagePath;
    private TextView changeText;
    private FrameLayout updateLayoutBtn;
    private LinearLayout orderLayout, pendingLayout, faqLayout, helpLayout;
    private ActivityResultLauncher<String> pickImage;

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

        pickImage = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri o) {
                        binding.profileImage.setImageURI(o);
                        profileViewModel.addImageToFireBaseStorage(o);
                    }
                }
        );

        inputsState(false, binding.userNameProfile);
        inputsState(false, binding.userNumberProfile);
        inputsState(false, binding.userAddressProfile);
        profileViewModel.getGetUserInfo();
        profileViewModel.getUserInfo.observe(requireActivity(), user -> {
                    binding.userNameProfile.setText(user.getUserName());
                    binding.userEmailProfile.setText(user.getEmailAddress());
                    binding.userNumberProfile.setText(user.getPhoneNumber());
                    binding.userAddressProfile.setText(user.getAddress());
                    if (isAdded()) {
                        if (user.getImagePath() != null) {
                            Glide.with(requireActivity()).load(user.getImagePath()).into(binding.profileImage);
                        } else if (Objects.equals(user.getImagePath(), "")){
                            binding.profileImage.setImageResource(R.drawable.toy_faces_);
                        }
                    }
                    inputsState(false, binding.userNameProfile);
                    inputsState(false, binding.userNumberProfile);
                    inputsState(false, binding.userAddressProfile);
                    binding.profileImage.setFocusable(false);
                    binding.profileImage.setFocusableInTouchMode(false);
                }
        );
        binding.changeText.setOnClickListener(v -> {
            String buttonText = binding.changeText.getText().toString();
            if (buttonText.equals("Change")) {
                setInputsEditable(true);
                binding.changeText.setText("Save");
                binding.profileImage.setOnClickListener(view1 -> {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    selectProfileImagesActivityResult.launch(intent);
                });
            } else if (buttonText.equals("Save")) {
                binding.changeProgressBar.setVisibility(View.VISIBLE);
                binding.changeText.setVisibility(View.INVISIBLE);
                if (inputsCheck()) {
                    profileViewModel.getIsImageProfileAdded().observe(requireActivity(), isImageAdded -> {
                        if (isImageAdded) {
                            profileViewModel.downloadImagePath.observe(requireActivity(), imagePath -> {
                                User updatedUser = new User(
                                        binding.userNameProfile.getText().toString(),
                                        binding.userAddressProfile.getText().toString(),
                                        imagePath,
                                        binding.userNumberProfile.getText().toString()
                                );
                                profileViewModel.modifyUserInfo(updatedUser);
                            });
                        }
                    });
                    profileViewModel.getIsUserAdded().observe(requireActivity(), isUserSaved -> {
                        if (isUserSaved) {
                            binding.changeProgressBar.setVisibility(View.GONE);
                            binding.changeText.setVisibility(View.VISIBLE);
                            binding.changeText.setText("Change");
                            setInputsEditable(false);
                        }
                    });
                }
            }
        });
    }

    private void inputsState(boolean b, EditText editText) {
        if (b) {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
        } else {
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
        }
    }

    private final ActivityResultLauncher<Intent> selectProfileImagesActivityResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Get the intent data
                    Intent intent = result.getData();

                    // Retrieve the image path (URI)
                    if (intent != null) {
                        profileViewModel.addImageToFireBaseStorage(intent.getData());
                        // Add image to the database
                        Log.d("TAG",intent.getData().toString());
                        // Set the image in the ImageView
                        binding.profileImage.setImageURI(intent.getData());
                    }
                }
            });


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
    private void setInputsEditable(boolean editable) {
        inputsState(editable, binding.userNameProfile);
        inputsState(editable, binding.userNumberProfile);
        inputsState(editable, binding.userAddressProfile);
        binding.profileImage.setClickable(editable); // Make the image clickable only when editable
        binding.profileImage.setFocusable(editable);
    }

}