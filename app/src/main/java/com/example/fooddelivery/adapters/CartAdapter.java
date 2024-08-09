package com.example.fooddelivery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.fooddelivery.R;
import com.example.fooddelivery.models.Post;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Post> postList;
    Context context;

    private final AsyncListDiffer<Post> mDiffer = new AsyncListDiffer<>(this, DIFF_CALLBACK);
    public void submitList(List<Post> list) {
        mDiffer.submitList(list);
        postList = new ArrayList<>(list);

    }

    public CartAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(
               R.layout.cart_items,
                parent
                ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.setCartList(mDiffer.getCurrentList().get(position), context);

    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
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
        }
        void setCartList(Post post, Context context){
            Glide.with(context).load(post.getImage()).into(cartImage);
            cartTitle.setText(post.getTitle());
            cartPrice.setText(post.getPrice());
        }
    }
    public static final DiffUtil.ItemCallback<Post> DIFF_CALLBACK
            = new DiffUtil.ItemCallback<Post>() {
        @Override
        public boolean areItemsTheSame(
                @NonNull Post oldPost, @NonNull Post newPost) {
            // User properties may have changed if reloaded from the DB, but ID is fixed
            return oldPost.getId() == newPost.getId();
        }
        @Override
        public boolean areContentsTheSame(
                @NonNull Post oldPost, @NonNull Post newPost) {
            // NOTE: if you use equals, your object must properly override Object#equals()
            // Incorrectly returning false here will result in too many animations.
            return oldPost.equals(newPost);
        }
    };
}
