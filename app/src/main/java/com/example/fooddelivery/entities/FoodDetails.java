package com.example.fooddelivery.entities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.fooddelivery.R;
import com.example.fooddelivery.models.AddToCart;
import com.example.fooddelivery.models.Post;
import com.example.fooddelivery.viewmodel.AddToCartViewModel;
import com.example.fooddelivery.viewmodel.DetailViewModel;


public class FoodDetails extends AppCompatActivity {
    private  ImageView coverImage, backImage, likeImage;
    private TextView title, price;
    private Button addCartButton;
    private Post availablePost;
    private DetailViewModel detailViewModel;
    private AddToCartViewModel toCartViewModel;
    private Boolean isLiked;
    private ProgressBar cartProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        coverImage = findViewById(R.id.imagePost);
        backImage = findViewById(R.id.backPostImage);
        likeImage = findViewById(R.id.favoritePostImage);
        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        addCartButton = findViewById(R.id.cartButton);
        cartProgress = findViewById(R.id.cartProgressBar);

        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        toCartViewModel = new ViewModelProvider(this).get(AddToCartViewModel.class);
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
        setAddToCartButton(finalPost);
        isLiked = false;
        detailViewModel.getSavedPost(new AddToCart(finalPost, 1));
        detailViewModel.getIsPostExist().observe(this, isPostExist -> {
            if (isPostExist) {
                isLiked = true;
                updateButtonState2();
            } else {

                likeImage.setOnClickListener(v -> {

                    if (isLiked) {
                        isLiked =false;
                        updateButtonState();
                        detailViewModel.removePost(new AddToCart(finalPost, 1));

                    } else {
                        detailViewModel.addPostToSave(new AddToCart(finalPost, 1));
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

    private void setAddToCartButton(Post post) {
        addCartButton.setOnClickListener(v -> {
            addCartButton.setText("");
            cartProgress.setVisibility(View.VISIBLE);
            toCartViewModel.addPostToCart(new AddToCart(post, 1));
        });
        toCartViewModel.getIsPostAddedToCart().observe(this, isPostAddedToCart -> {
            if (isPostAddedToCart){
                addCartButton.setText("Add To Cart");
                cartProgress.setVisibility(View.GONE);
                Toast.makeText(this, "Added To Cart successfully!!", Toast.LENGTH_SHORT).show();
            }else {
                addCartButton.setText("Add To Cart");
                cartProgress.setVisibility(View.GONE);
                Toast.makeText(this, "previously added!", Toast.LENGTH_SHORT).show();
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