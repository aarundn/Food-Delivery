package com.example.fooddelivery.entities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fooddelivery.R;

public class MoreActivity extends AppCompatActivity {
    private ImageView backFromMoreImage;
    private TextView moreTitleTxt;
    private RecyclerView moreRecyclerView;
    private ProgressBar moreProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
    }
}