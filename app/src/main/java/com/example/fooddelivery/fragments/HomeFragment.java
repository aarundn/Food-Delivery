package com.example.fooddelivery.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fooddelivery.R;
import com.example.fooddelivery.adapters.FoodAdapter;
import com.example.fooddelivery.entities.FoodDetails;
import com.example.fooddelivery.entities.SearchActivity;
import com.example.fooddelivery.listeners.HomeViewpagerOnClickListener;
import com.example.fooddelivery.models.Post;
import com.example.fooddelivery.viewmodel.HomeViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment implements HomeViewpagerOnClickListener {



    private List<Post> filteredPosts;
    private List<Post> allPosts;
    private TabLayout tableLayout;
    private FoodAdapter foodAdapter;
    private EditText searchEditText;
    private LinearLayout searchLayout;
    private HomeViewModel homeViewModel;
    private boolean isDataFetched;
    private ProgressBar progressBar;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        tableLayout = view.findViewById(R.id.tabLayout);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        allPosts = new ArrayList<>();

        foodAdapter = new FoodAdapter(this,requireContext());
        homeViewModel.getHomePosts("Foods").observe(getViewLifecycleOwner(), postes -> {
            filteredPosts = new ArrayList<>(postes);
            foodAdapter.submitList(filteredPosts);
            foodAdapter.notifyDataSetChanged();
        });

//        homeViewModel.getHomePosts().observe(getViewLifecycleOwner(), postes -> {
//            allPosts.addAll(postes);
//            filteredPosts = new ArrayList<>();
//            fillterPosts("Foods");
//            foodAdapter.submitList(filteredPosts);
//            foodAdapter.notifyDataSetChanged();
//        });



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

        homeViewModel.getIsInfoFetched().observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });


        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                homeViewModel.getIsInfoFetched().observe(requireActivity(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean){
                            progressBar.setVisibility(View.GONE);
                        }else {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });
                String tabName = Objects.requireNonNull(tab.getText()).toString();
                homeViewModel.getHomePosts(tabName).observe(getViewLifecycleOwner(), postes -> {
                    filteredPosts = new ArrayList<>(postes);
                    foodAdapter.submitList(filteredPosts);
                    foodAdapter.notifyDataSetChanged();

                });
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setPostViewPager(postViewPager);
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


    @Override
    public void OnItemClicked(int position) {

        Intent intent = new Intent(getContext(), FoodDetails.class);
        intent.putExtra("image",  filteredPosts.get(position).getImage());
        intent.putExtra("title",filteredPosts.get(position).getTitle());
        intent.putExtra("price",String.valueOf(filteredPosts.get(position).getPrice()));
        startActivity(intent);
    }
}