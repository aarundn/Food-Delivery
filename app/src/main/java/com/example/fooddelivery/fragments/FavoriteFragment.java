package com.example.fooddelivery.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.R;
import com.example.fooddelivery.adapters.AddToCartAdapter;
import com.example.fooddelivery.adapters.CartAdapter;
import com.example.fooddelivery.helper.MyButtonClickListener;
import com.example.fooddelivery.helper.MySwipeHelper;
import com.example.fooddelivery.viewmodel.DetailViewModel;

import java.util.List;


public class FavoriteFragment extends Fragment {



    private RecyclerView favRecyclerView;
    private ImageView backButton;
    private Button addToCartButton;
    private DetailViewModel detailViewModel;
    private AddToCartAdapter cartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favRecyclerView = view.findViewById(R.id.favRecyclerView);
        addToCartButton = view.findViewById(R.id.favAddCartButton);
        backButton = view.findViewById(R.id.favBackImage);

        cartAdapter = new AddToCartAdapter(requireContext());
        detailViewModel = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);

        detailViewModel.getAllSavedPost().observe(requireActivity(), postList -> {
            cartAdapter.submitList(postList);
            cartAdapter.notifyDataSetChanged();
        });
        setRecyclerView();
        MySwipeHelper swipeHelper = new MySwipeHelper(getContext(), favRecyclerView, 150) {
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

    private void setRecyclerView() {
        favRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(),
                LinearLayoutManager.VERTICAL,false));
        favRecyclerView.setAdapter(cartAdapter);
    }
}