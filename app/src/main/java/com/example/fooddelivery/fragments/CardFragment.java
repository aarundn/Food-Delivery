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
import com.example.fooddelivery.models.poste;

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
        cartAdapter = new CartAdapter(getPosts());
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.setAdapter(cartAdapter);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToShowButtonsCallback());
//        itemTouchHelper.attachToRecyclerView(cartRecyclerView);

        MySwipeHelper swipeHelper = new MySwipeHelper(getContext(), cartRecyclerView, 150) {
            @Override
            public void initiatMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
                buffer.add(new MySwipeHelper.MyButton(
                        getContext(),
                        R.drawable.d_1,
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

    private List<poste> getPosts(){
        List<poste> posts = new ArrayList<>();
        poste post = new poste("Crispy chicken",R.drawable.crispy_chicken,200.5f,"Foods");
        poste post1 = new poste("Veggie Supreme",R.drawable.veggie_supreme,350f,"Foods");
        poste post2 = new poste("Crispy chicken",R.drawable.crispy_chicken,200.5f,"Foods");
        poste post3 = new poste("Drink1",R.drawable.crispy_chicken,200.5f,"Drinks");
        poste post4 = new poste("Drink2",R.drawable.veggie_supreme,350f,"Drinks");
        poste post5 = new poste("Crispy snacks",R.drawable.crispy_chicken,200.5f,"Snacks");
        poste post6 = new poste("Crispy snacks1",R.drawable.crispy_chicken,200.5f,"Snacks");
        poste post7 = new poste("Veggie Supreme",R.drawable.veggie_supreme,350f,"Foods");
        poste post8 = new poste("Crispy chicken",R.drawable.crispy_chicken,200.5f,"Foods");
        poste post10 = new poste("sauce",R.drawable.crispy_chicken,200.5f,"Sauce");
        poste post11 = new poste("sauce1",R.drawable.veggie_supreme,350f,"Sauce");
        poste post12 = new poste("Crispy snacks2",R.drawable.crispy_chicken,200.5f,"Snacks");
        posts.add(post);
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);
        posts.add(post6);
        posts.add(post7);
        posts.add(post8);
        posts.add(post10);
        posts.add(post11);
        posts.add(post12);

        return posts;
    }
}