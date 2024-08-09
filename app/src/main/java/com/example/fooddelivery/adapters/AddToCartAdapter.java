package com.example.fooddelivery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooddelivery.R;
import com.example.fooddelivery.listeners.OnItemClickListener;
import com.example.fooddelivery.models.AddToCart;
import com.example.fooddelivery.models.Post;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartAdapter.CartViewHolder> {

    Context context;
    private final OnItemClickListener onItemClicked;
    private final AsyncListDiffer<AddToCart> mDiffer = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    public void submitList(List<AddToCart> list) {
        mDiffer.submitList(list);


    }

    public AddToCartAdapter(Context context, OnItemClickListener onItemClicked) {
        this.context = context;
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.cart_items,
                parent
                , false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
            AddToCart post = mDiffer.getCurrentList().get(position);
        holder.setCartList(post, context);

        holder.constrainLayout.setOnClickListener(v -> {
            onItemClicked.onItemClicked(holder.getLayoutPosition(), post.getPost());
        });
    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout constrainLayout;
        private RoundedImageView cartImage;
        private TextView cartPrice, cartTitle, cartPlusSign, cartMinusSign, cartCounter;
        private Button delete, update;
        public LinearLayout buttonsLayout;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            cartImage = itemView.findViewById(R.id.cartImageTv);
            cartTitle = itemView.findViewById(R.id.titleCartTv);
            cartPrice = itemView.findViewById(R.id.priceCartTv);
            cartPlusSign = itemView.findViewById(R.id.plusSignCartTv);
            cartMinusSign = itemView.findViewById(R.id.minusSignCartTv);
            cartCounter = itemView.findViewById(R.id.counterCartTv);
            constrainLayout = itemView.findViewById(R.id.cartConstraintLayout);
        }

        void setCartList(AddToCart post, Context context) {
            Glide.with(context).load(post.getPost().getImage()).into(cartImage);
            cartTitle.setText(post.getPost().getTitle());
            cartPrice.setText(post.getPost().getPrice());
        }
    }

    public static final DiffUtil.ItemCallback<AddToCart> DIFF_CALLBACK
            = new DiffUtil.ItemCallback<AddToCart>() {
        @Override
        public boolean areItemsTheSame(
                @NonNull AddToCart oldPost, @NonNull AddToCart newPost) {
            // User properties may have changed if reloaded from the DB, but ID is fixed
            return oldPost.getPost().getId() == newPost.getPost().getId();
        }

        @Override
        public boolean areContentsTheSame(
                @NonNull AddToCart oldPost, @NonNull AddToCart newPost) {
            // NOTE: if you use equals, your object must properly override Object#equals()
            // Incorrectly returning false here will result in too many animations.
            return oldPost.equals(newPost);
        }
    };
}
