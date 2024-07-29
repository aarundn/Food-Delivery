package com.example.fooddelivery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.fooddelivery.R;
import com.example.fooddelivery.models.Post;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Post> postList;

    public CartAdapter(List<Post> postList) {
        this.postList = postList;
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
        holder.setCartList(postList.get(position));

    }

    @Override
    public int getItemCount() {
        return postList.size();
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
        void setCartList(Post post){
//            Glide.with(this).load(post)
            cartTitle.setText(post.getTitle());
            cartPrice.setText(String.valueOf(post.getPrice()));
        }
    }
}
