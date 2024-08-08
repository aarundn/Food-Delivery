package com.example.fooddelivery.entities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.example.fooddelivery.R;
import com.example.fooddelivery.adapters.SearchAdapter;
import com.example.fooddelivery.listeners.OnItemClickListener;
import com.example.fooddelivery.models.Post;
import com.example.fooddelivery.viewmodel.SearchAndMoreViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView searchRecycler;
    private SearchAdapter searchAdapter;
    private EditText searchInput;
    private LinearLayout itemNotFound;
    private SearchAndMoreViewModel searchAndMoreViewModel;
    private List<Post> list;
    private ProgressBar searchProgressBar;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchRecycler = findViewById(R.id.searchRecyclerView);
        searchInput = findViewById(R.id.searchInput);
        itemNotFound = findViewById(R.id.itemNotFound);
        searchProgressBar = findViewById(R.id.searchProgressBar);

        searchAndMoreViewModel = new ViewModelProvider(this).get(SearchAndMoreViewModel.class);
        list = new ArrayList<>();
        searchAdapter = new SearchAdapter(this, getApplicationContext());
        timer = new Timer();

        searchAndMoreViewModel.getAllPostList().observe(this, posts -> {
            searchAdapter.submitList(posts);
            list = posts;
            searchAdapter.notifyDataSetChanged();
        });


        searchAndMoreViewModel.getIsAllDataFetched().observe(this, isDataFetched -> {
            searchProgressBar.setVisibility(View.VISIBLE);
            if (isDataFetched) {
                searchProgressBar.setVisibility(View.GONE);
            }
        });



        setRecyclerView();
        setSearchBar();

    }

    private void setRecyclerView() {
        searchRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        searchRecycler.setAdapter(searchAdapter);
    }

    private void setSearchBar() {
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {


                        handler.post(new Runnable() {
                            @Override
                            public void run() {searchAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                },500);
                searchProgressBar.setVisibility(View.VISIBLE);
                searchAndMoreViewModel.searchPosts(s.toString()).observe(SearchActivity.this, postList -> {
                    searchAdapter.submitList(postList);

                });
                searchAndMoreViewModel.getIsSearchedDataFetched().observe(SearchActivity.this, isDataFetched -> {

                    if (isDataFetched) {
                        itemNotFound.setVisibility(View.GONE);
                        searchProgressBar.setVisibility(View.GONE);
                        searchAdapter.notifyDataSetChanged();
                    } else {
                        searchAndMoreViewModel.getIsListEmpty().observe(SearchActivity.this, aBoolean -> {
                            if (!s.toString().isEmpty()){
                                if (aBoolean){
                                    itemNotFound.setVisibility(View.VISIBLE);
                                    searchAdapter.notifyDataSetChanged();
                                    searchProgressBar.setVisibility(View.GONE);
                                }
                            } else {
                                itemNotFound.setVisibility(View.GONE);
                                searchAdapter.submitList(list);
                                searchAdapter.notifyDataSetChanged();
                            }
                        });                    }

                });
                searchAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onItemClicked(int position, Post post) {
        Intent intent = new Intent(getApplicationContext(), FoodDetails.class);
        intent.putExtra("image", post.getImage());
        intent.putExtra("title", post.getTitle());
        intent.putExtra("price", String.valueOf(post.getPrice()));
        startActivity(intent);
    }
}