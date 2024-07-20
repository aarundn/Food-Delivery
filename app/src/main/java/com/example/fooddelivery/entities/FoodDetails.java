package com.example.fooddelivery.entities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fooddelivery.R;
import com.example.fooddelivery.models.poste;


public class FoodDetails extends AppCompatActivity {
    private  ImageView coverImage, backImage, likeImage;
    private TextView title, price;
    private Button addCartButton;
    private poste availablePost;

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

        SetListeners();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            coverImage.setImageResource(bundle.getInt("image"));
            title.setText(bundle.getString("title"));
            price.setText(bundle.getString("price"));

        }
    }

    private void SetListeners() {
        likeImage.setOnClickListener(v -> {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                coverImage.setImageResource(bundle.getInt("image"));
                title.setText(bundle.getString("title"));
                price.setText(bundle.getString("price"));
            }
            likeImage.setImageResource(R.drawable.solid_heart);
        });

        backImage.setOnClickListener(v -> {
            startActivity( new Intent(getApplicationContext(), MainActivity.class));
        });
    }


}