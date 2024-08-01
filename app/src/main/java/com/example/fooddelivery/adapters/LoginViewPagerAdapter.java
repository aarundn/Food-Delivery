package com.example.fooddelivery.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fooddelivery.fragments.loginfragments.SignInFragment;
import com.example.fooddelivery.fragments.loginfragments.SignUpFragment;

public class LoginViewPagerAdapter extends FragmentStateAdapter {

    public LoginViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SignInFragment();
            case 1:
                return new SignUpFragment();
            default:
                throw new IllegalStateException("Unexpected position " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
