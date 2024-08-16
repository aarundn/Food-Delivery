package com.example.fooddelivery.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fooddelivery.R;
import com.example.fooddelivery.adapters.AddToCartAdapter;
import com.example.fooddelivery.helper.MyButtonClickListener;
import com.example.fooddelivery.helper.MySwipeHelper;
import com.example.fooddelivery.listeners.OnItemQuantityListener;
import com.example.fooddelivery.models.AddToCart;
import com.example.fooddelivery.viewmodel.AddToCartViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class CardFragment extends Fragment implements OnItemQuantityListener {
    private RecyclerView cartRecyclerView;
    private ImageView backImage;
    private AddToCartAdapter cartAdapter;
    private ProgressBar proceedProgressBar, cartProgressBar;
    private Button proceedButton;
    private AddToCartViewModel addToCartViewModel;
    private LinearLayout noItemFoundInCart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        backImage = view.findViewById(R.id.backImage);
        proceedProgressBar = view.findViewById(R.id.proceedProgressBar);
        proceedButton = view.findViewById(R.id.proceedButton);
        cartProgressBar = view.findViewById(R.id.cartProgressBar);
        noItemFoundInCart = view.findViewById(R.id.noItemInCartFound);
        cartAdapter = new AddToCartAdapter(requireContext(), this);
        addToCartViewModel = new ViewModelProvider(requireActivity()).get(AddToCartViewModel.class);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.setAdapter(cartAdapter);
        cartProgressBar.setVisibility(View.VISIBLE);
        getAllPostsToCart();


        MySwipeHelper swipeHelper = new MySwipeHelper(getContext(), cartRecyclerView, 150) {
            @Override
            public void initiatMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {

                buffer.add(new MyButton(
                        getContext(),
                        R.drawable.d_1,
                        Color.parseColor("#00FFFFFF"),
                        new MyButtonClickListener(){
                            @Override
                            public void onClick(int pos) {
                                if (pos >= 0 && pos < cartAdapter.getCurrentList().size()) {
                                    AddToCart post = cartAdapter.getCurrentList().get(pos);
                                    addToCartViewModel.removeFromCart(post);
                                    List<AddToCart> currentList = new ArrayList<>(cartAdapter.getCurrentList());
                                    currentList.remove(pos);
                                    cartAdapter.submitList(currentList);
                                    cartAdapter.notifyItemChanged(pos);
                                    cartAdapter.notifyDataSetChanged();
                                    if (currentList.isEmpty()){
                                        noItemFoundInCart.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                ));
            }
        };

    }

    private void getAllPostsToCart() {
        cartProgressBar.setVisibility(View.VISIBLE);
        addToCartViewModel.getAllCartPost().observe(requireActivity(), posts -> {

            if (posts.isEmpty()){
                noItemFoundInCart.setVisibility(View.VISIBLE);
                cartProgressBar.setVisibility(View.GONE);
            }else {
                cartAdapter.submitList(posts);
                cartProgressBar.setVisibility(View.GONE);
            }
        });
        addToCartViewModel.getIsAllPostGet().observe(requireActivity(), isAllPostGet -> {
            if (isAllPostGet){
                cartProgressBar.setVisibility(View.GONE);
                noItemFoundInCart.setVisibility(View.GONE);
            }else {
                    cartProgressBar.setVisibility(View.VISIBLE);
                    noItemFoundInCart.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        noItemFoundInCart.setVisibility(View.GONE);
    }

    @Override
    public void onQuantityListener(String docId, int quantity) {
        addToCartViewModel.updateQuantity(docId, quantity);
    }
}