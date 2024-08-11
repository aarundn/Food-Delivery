package com.example.fooddelivery.entities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.fooddelivery.R;
import com.example.fooddelivery.models.AddToCart;
import com.example.fooddelivery.models.Post;
import com.example.fooddelivery.viewmodel.DetailViewModel;


public class FoodDetails extends AppCompatActivity {
    private  ImageView coverImage, backImage, likeImage;
    private TextView title, price;
    private Button addCartButton;
    private Post availablePost;
    private DetailViewModel detailViewModel;
    private Boolean isLiked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        coverImage = findViewById(R.id.imagePost);
        backImage = findViewById(R.id.backPostImage);
        likeImage = findViewById(R.id.favoritePostImage);
        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        addCartButton = findViewById(R.id.addCartButton);
        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        SetListeners();

        Post post = new Post();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String id = bundle.getString("id");
            String title1 = bundle.getString("title");
            String category = bundle.getString("category");
            String Image = bundle.getString("image");
            String price1 = bundle.getString("price");
            Glide.with(getApplicationContext()).load(Image).into(coverImage);
            title.setText(title1);
            price.setText(price1);
            post = new Post(id, title1, Image, price1, category);

        }
        Post finalPost = post;

        isLiked = false;
        detailViewModel.getSavedPost(new AddToCart(finalPost));
        detailViewModel.getIsPostExist().observe(this, isPostExist -> {
            if (isPostExist) {
                isLiked = true;
                updateButtonState2();
            } else {

                likeImage.setOnClickListener(v -> {

                    if (isLiked) {
                        isLiked =false;
                        updateButtonState();
                        detailViewModel.removePost(new AddToCart(finalPost));

                    } else {
                        detailViewModel.addPostToSave(new AddToCart(finalPost));
                        isLiked = true;
                        updateButtonState();
                        detailViewModel.getIsPostSaved().observe(this, isPostSaved -> {
                            if (isPostSaved) {
                                isLiked = true;
                            }
                        });
                    }
                });
            }
        });


    }
    private void SetListeners() {

        backImage.setOnClickListener(v -> {
            startActivity( new Intent(getApplicationContext(), MainActivity.class));
        });
    }

    private void updateButtonState(){
        if (!isLiked){
            likeImage.setImageDrawable(getDrawable(R.drawable.blach_heart));
            Toast.makeText(this, "Post unsaved :(", Toast.LENGTH_SHORT).show();
        }else {
            likeImage.setImageDrawable(getDrawable(R.drawable.red_heart));
            Toast.makeText(this, "post Saved :)", Toast.LENGTH_SHORT).show();
        }

    }
    private void updateButtonState2() {
        if (isLiked) {
            likeImage.setImageDrawable(getDrawable(R.drawable.red_heart));
        } else {
            likeImage.setImageDrawable(getDrawable(R.drawable.blach_heart));

        }
    }
}