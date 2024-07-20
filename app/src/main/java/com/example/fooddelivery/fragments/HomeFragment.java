package com.example.fooddelivery.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fooddelivery.R;
import com.example.fooddelivery.adapters.foodAdapter;
import com.example.fooddelivery.entities.FoodDetails;
import com.example.fooddelivery.entities.SearchActivity;
import com.example.fooddelivery.listeners.HomeViewpagerOnClickListener;
import com.example.fooddelivery.models.poste;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment implements HomeViewpagerOnClickListener {



    private List<poste> filteredPosts;
    private TabLayout tableLayout;
    private foodAdapter foodAdapter;
    private EditText searchEditText;
    private LinearLayout searchLayout;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tableLayout = view.findViewById(R.id.tabLayout);
        filteredPosts = new ArrayList<>(getPosts());
        foodAdapter = new foodAdapter(filteredPosts,this);

        RecyclerView postViewPager = view.findViewById(R.id.mainPager);
        searchEditText = view.findViewById(R.id.searchInput);
        searchLayout = view.findViewById(R.id.searchLayout);

        searchLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) getContext(),view.findViewById(R.id.searchLayout)
                    ,"transitionSearch"
            );
            startActivity(intent,compat.toBundle());
        });

        searchEditText.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) getContext(),view.findViewById(R.id.searchLayout)
                    ,"transitionSearch"
            );
            startActivity(intent,compat.toBundle());
        });

        setPostViewPager(postViewPager);
        fillterPosts("Foods");
        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabName = Objects.requireNonNull(tab.getText()).toString();

                fillterPosts(tabName);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void fillterPosts(String tabName) {
        filteredPosts.clear();
            for (poste post : getPosts()) {
                // Assuming each post has a category field
                if (post.getCategory().equals(tabName)) {
                    filteredPosts.add(post);

                }
            }
            foodAdapter.notifyDataSetChanged(); // Notify adapter about data change
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment =inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        return fragment;
    }
    private void setPostViewPager(RecyclerView postViewPager){
        LinearLayoutManager layoutManager =  new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,false);
        postViewPager.setLayoutManager(layoutManager);
        postViewPager.setAdapter(foodAdapter);

    }
    private List<poste> getPosts(){
        List<poste> posts = new ArrayList<>();
        poste post = new poste("Crispy chicken",R.drawable.crispy_chicken,200.5f,"Foods");
        poste post1 = new poste("Veggie Supreme",R.drawable.veggie_supreme,350f,"Foods");
        poste post2 = new poste("Crispy chicken",R.drawable.crispy_chicken,200.5f,"Foods");
        poste post3 = new poste("Drink1",R.drawable.crispy_chicken,200.5f,"Drinks");
        poste post4 = new poste("Drink2",R.drawable.veggie_supreme,350f,"Drinks");
        poste post5 = new poste("Crispy snacks",R.drawable.crispy_chicken,200.5f,"Snacks");
        poste post6 = new poste("Crispy snacks1",R.drawable.crispy_chicken,200.5f,"Snacks");
        poste post7 = new poste("Veggie Supreme",R.drawable.veggie_supreme,350f,"Foods");
        poste post8 = new poste("Crispy chicken",R.drawable.crispy_chicken,200.5f,"Foods");
        poste post10 = new poste("sauce",R.drawable.crispy_chicken,200.5f,"Sauce");
        poste post11 = new poste("sauce1",R.drawable.veggie_supreme,350f,"Sauce");
        poste post12 = new poste("Crispy snacks2",R.drawable.crispy_chicken,200.5f,"Snacks");
        posts.add(post);
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);
        posts.add(post6);
        posts.add(post7);
        posts.add(post8);
        posts.add(post10);
        posts.add(post11);
        posts.add(post12);

        return posts;
    }

    @Override
    public void OnItemClicked(int position) {

        Intent intent = new Intent(getContext(), FoodDetails.class);
        intent.putExtra("image",  filteredPosts.get(position).getImage());
        intent.putExtra("title",filteredPosts.get(position).getTitle());
        intent.putExtra("price",String.valueOf(filteredPosts.get(position).getPrice()));
        startActivity(intent);
    }
}