 package com.example.fooddelivery.entities;

 import android.content.Intent;
 import android.os.Bundle;
 import android.text.Editable;
 import android.text.TextWatcher;
 import android.view.View;
 import android.widget.EditText;
 import android.widget.LinearLayout;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.recyclerview.widget.RecyclerView;
 import androidx.recyclerview.widget.StaggeredGridLayoutManager;


 import com.example.fooddelivery.R;
 import com.example.fooddelivery.adapters.searchAdapter;
 import com.example.fooddelivery.listeners.OnItemClickListener;
 import com.example.fooddelivery.models.Post;

 import java.util.ArrayList;
 import java.util.List;

 public class SearchActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView searchRecycler;
    private searchAdapter SearchAdapter;
    private EditText searchInput;
    private LinearLayout itemNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchRecycler = findViewById(R.id.searchRecyclerView);
        searchInput = findViewById(R.id.searchInput);
        itemNotFound = findViewById(R.id.itemNotFound);

        SearchAdapter = new searchAdapter(getPosts(),this,getApplicationContext());
        searchRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        searchRecycler.setAdapter(SearchAdapter);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SearchAdapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {

                SearchAdapter.searchPosts(s.toString());
                if (SearchAdapter.isEmptyList() && !s.toString().isEmpty()){
                    itemNotFound.setVisibility(View.VISIBLE);
                }else {
                    itemNotFound.setVisibility(View.INVISIBLE);
                }

            }
        });
    }
     private List<Post> getPosts(){
         List<Post> posts = new ArrayList<>();
//         Post post = new Post("Crispy chicken",R.drawable.crispy_chicken,200.5f,"Foods");
//         Post post1 = new Post("Veggie Supreme",R.drawable.veggie_supreme,350f,"Foods");
//         Post post2 = new Post("Crispy chicken",R.drawable.crispy_chicken,200.5f,"Foods");
//         Post post3 = new Post("Drink1",R.drawable.crispy_chicken,200.5f,"Drinks");
//         Post post4 = new Post("Drink2",R.drawable.veggie_supreme,350f,"Drinks");
//         Post post5 = new Post("Crispy snacks",R.drawable.crispy_chicken,200.5f,"Snacks");
//         Post post6 = new Post("Crispy snacks1",R.drawable.crispy_chicken,200.5f,"Snacks");
//         Post post7 = new Post("Veggie Supreme",R.drawable.veggie_supreme,350f,"Foods");
//         Post post8 = new Post("Crispy chicken",R.drawable.crispy_chicken,200.5f,"Foods");
//         Post post10 = new Post("sauce",R.drawable.crispy_chicken,200.5f,"Sauce");
//         Post post11 = new Post("sauce1",R.drawable.veggie_supreme,350f,"Sauce");
//         Post post12 = new Post("Crispy snacks2",R.drawable.crispy_chicken,200.5f,"Snacks");
//         posts.add(post);
//         posts.add(post1);
//         posts.add(post2);
//         posts.add(post3);
//         posts.add(post4);
//         posts.add(post5);
//         posts.add(post6);
//         posts.add(post7);
//         posts.add(post8);
//         posts.add(post10);
//         posts.add(post11);
//         posts.add(post12);

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