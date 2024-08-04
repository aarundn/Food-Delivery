 package com.example.fooddelivery.entities;

 import android.content.Intent;
 import android.os.Bundle;
 import android.text.Editable;
 import android.text.TextWatcher;
 import android.view.View;
 import android.widget.EditText;
 import android.widget.LinearLayout;

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

 public class SearchActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView searchRecycler;
    private SearchAdapter searchAdapter;
    private EditText searchInput;
    private LinearLayout itemNotFound;
    private SearchAndMoreViewModel searchAndMoreViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchRecycler = findViewById(R.id.searchRecyclerView);
        searchInput = findViewById(R.id.searchInput);
        itemNotFound = findViewById(R.id.itemNotFound);

        searchAndMoreViewModel = new ViewModelProvider(this).get(SearchAndMoreViewModel.class);



        searchAdapter = new SearchAdapter(this,getApplicationContext());
        searchAndMoreViewModel.getAllPostList().observe(this, posts -> {
            searchAdapter.submitList(posts);
        });
        searchRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        searchRecycler.setAdapter(searchAdapter);

        searchAndMoreViewModel.getIsAllDataFetched().observe(this, isDataFetched -> {

        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchAdapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {

                searchAdapter.searchPosts(s.toString());
                if (searchAdapter.isEmptyList() && !s.toString().isEmpty()){
                    itemNotFound.setVisibility(View.VISIBLE);
                }else {
                    itemNotFound.setVisibility(View.INVISIBLE);
                }

            }
        });
    }
     private List<Post> getPosts(){
         List<Post> posts = new ArrayList<>();
         return posts;
     }


     @Override
     public void onItemClicked(int position, Post post) {
         Intent intent = new Intent(getApplicationContext(), FoodDetails.class);
         intent.putExtra("image",  post.getImage());
         intent.putExtra("title",post.getTitle());
         intent.putExtra("price",String.valueOf(post.getPrice()));
         startActivity(intent);
     }
 }