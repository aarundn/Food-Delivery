package com.example.fooddelivery.entities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fooddelivery.R;
import com.example.fooddelivery.adapters.SearchAdapter;
import com.example.fooddelivery.helper.Constants;
import com.example.fooddelivery.listeners.OnItemClickListener;
import com.example.fooddelivery.models.Post;
import com.example.fooddelivery.viewmodel.SearchAndMoreViewModel;

public class MoreActivity extends AppCompatActivity implements OnItemClickListener {
    private ImageView backFromMoreImage;
    private TextView moreTitleTxt;
    private RecyclerView moreRecyclerView;
    private ProgressBar moreProgressBar;
    private SearchAdapter moreAdapter;
    private SearchAndMoreViewModel searchAndMoreViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        backFromMoreImage = findViewById(R.id.backFromMore);
        moreTitleTxt = findViewById(R.id.seeMoreTitle);
        moreRecyclerView = findViewById(R.id.moreRecyclerView);
        moreProgressBar = findViewById(R.id.moreProgressBar);


        searchAndMoreViewModel = new ViewModelProvider(this).get(SearchAndMoreViewModel.class);

        moreAdapter = new SearchAdapter(this, getApplicationContext());
        setRecyclerView();

        String category = getIntent().getStringExtra(Constants.POST_CATEGORY);
        moreTitleTxt.setText(category);
        searchAndMoreViewModel.getListByCategory(category).observe(this, postList -> {
            moreAdapter.submitList(postList);
            Log.d("LIST", String.valueOf(postList.size()));
            moreAdapter.notifyDataSetChanged();
        });
        moreProgressBar.setVisibility(View.VISIBLE);
        searchAndMoreViewModel.getIsCategoryPostsFetched().observe(this, isFetched ->{
            if (isFetched){
                moreProgressBar.setVisibility(View.GONE);
            } else {
                moreProgressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setRecyclerView() {
        moreRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        moreRecyclerView.setAdapter(moreAdapter);
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