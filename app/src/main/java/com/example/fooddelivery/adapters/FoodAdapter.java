package com.example.fooddelivery.adapters;

import static com.example.fooddelivery.adapters.FoodAdapter.FoodViewHolder.DIFF_CALLBACK;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.fooddelivery.R;
import com.example.fooddelivery.listeners.HomeViewpagerOnClickListener;
import com.example.fooddelivery.models.Post;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private final AsyncListDiffer<Post> mDiffer = new AsyncListDiffer<>(this, DIFF_CALLBACK);
    private Context context;

    private final HomeViewpagerOnClickListener homeInterface;


    public void submitList(List<Post> list) {
        mDiffer.submitList(list);
    }
    public FoodAdapter(HomeViewpagerOnClickListener homeInterface, Context context) {
        this.homeInterface = homeInterface;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(
                R.layout.food_items
                ,parent
                ,false
        );
        return new FoodViewHolder(view,homeInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Post post = mDiffer.getCurrentList().get(position);
        holder.setPostList(post,context);

    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

     static class FoodViewHolder extends RecyclerView.ViewHolder{
        private final RoundedImageView imageView;
        private final TextView postTitle, price;
        public FoodViewHolder(@NonNull View itemView, HomeViewpagerOnClickListener homeInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.foodImageTv);
            postTitle = itemView.findViewById(R.id.foodTitleTv);
            price = itemView.findViewById(R.id.foodPriceTv);

            itemView.setOnClickListener(v -> {
               if(homeInterface != null){
                   int position = getAdapterPosition();
                   if (position != RecyclerView.NO_POSITION){
                       homeInterface.OnItemClicked(position);
                   }
               }
            });

        }

        void setPostList(Post post, Context context){
            Glide.with(context).load(post.getImage()).into(imageView);
            postTitle.setText(post.getTitle());
            price.setText(post.getPrice());
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



}
