package com.example.fooddelivery.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fooddelivery.R;
import com.example.fooddelivery.adapters.CartAdapter;
import com.example.fooddelivery.helper.MyButtonClickListener;
import com.example.fooddelivery.helper.MySwipeHelper;
import com.example.fooddelivery.models.Post;

import java.util.ArrayList;
import java.util.List;


public class CardFragment extends Fragment {
    private RecyclerView cartRecyclerView;
    private ImageView backImage;
    private CartAdapter cartAdapter;

    public CardFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        backImage = view.findViewById(R.id.backImage);
//        cartAdapter = new CartAdapter();
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.setAdapter(cartAdapter);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToShowButtonsCallback());
//        itemTouchHelper.attachToRecyclerView(cartRecyclerView);

        MySwipeHelper swipeHelper = new MySwipeHelper(getContext(), cartRecyclerView, 150) {
            @Override
            public void initiatMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
                buffer.add(new MySwipeHelper.MyButton(
                        getContext(),
                        R.drawable.ss_1,
                        Color.parseColor("#00FFFFFF"),
                        new MyButtonClickListener(){
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(getContext(), "save Button clicked!", Toast.LENGTH_SHORT).show();
                            }
                        }
                ));
                buffer.add(new MyButton(
                        getContext(),
                        R.drawable.d_1,
                        Color.parseColor("#00FFFFFF"),
                        new MyButtonClickListener(){
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(getContext(), "delete Button clicked!", Toast.LENGTH_SHORT).show();
                                System.out.println("hello");
                            }
                        }
                ));
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false);
    }

//    private List<Post> getPosts(){
//
//
//        return posts;
//    }
}