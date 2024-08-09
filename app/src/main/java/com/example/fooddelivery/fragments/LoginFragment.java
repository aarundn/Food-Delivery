package com.example.fooddelivery.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddelivery.R;
import com.example.fooddelivery.adapters.LoginViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class LoginFragment extends Fragment {


    private LoginViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.loginViewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPagerAdapter = new LoginViewPagerAdapter(requireActivity());
        List<String> tabsList = new ArrayList<>();
        tabsList.add("Login");
        tabsList.add("Register");
        viewPager2.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2,((tab, position) -> {
            tab.setText(tabsList.get(position));
        })).attach();
    }
}