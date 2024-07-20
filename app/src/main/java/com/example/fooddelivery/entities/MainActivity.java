package com.example.fooddelivery.entities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fooddelivery.R;
import com.example.fooddelivery.fragments.CardFragment;
import com.example.fooddelivery.fragments.FavoriteFragment;
import com.example.fooddelivery.fragments.HomeFragment;
import com.example.fooddelivery.fragments.profileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottonNav);


//        setPostViewPager();
        replaceFragment(new HomeFragment());
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.heart:
                    replaceFragment(new FavoriteFragment());
                    break;
                case R.id.user:
                    replaceFragment(new profileFragment());
                    break;
                case R.id.history:
                    replaceFragment(new CardFragment());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            return true;
        });

//        setRecyclerView();
    }
//    private void setRecyclerView(){
//        RecyclerView recyclerView = findViewById(R.id.mainPager);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
//                LinearLayoutManager.HORIZONTAL,false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(new foodAdapter(getPosts()));
//    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }
}