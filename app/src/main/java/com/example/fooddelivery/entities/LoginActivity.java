package com.example.fooddelivery.entities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.fooddelivery.R;
import com.example.fooddelivery.adapters.LoginViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private LoginViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewPager2 = findViewById(R.id.loginViewPager);
        tabLayout = findViewById(R.id.tabLayout);
        viewPagerAdapter = new LoginViewPagerAdapter(this);
        List<String> tabsList = new ArrayList<>();
        tabsList.add("Login");
        tabsList.add("Register");
        viewPager2.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2,((tab, position) -> {
            tab.setText(tabsList.get(position));
        })).attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}