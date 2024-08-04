package com.example.fooddelivery.adapters;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.fooddelivery.R;

import com.example.fooddelivery.listeners.OnItemClickListener;
import com.example.fooddelivery.models.Post;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.FoodViewHolder> {

    private final AsyncListDiffer<Post> mDiffer = new AsyncListDiffer<>(this, DIFF_CALLBACK);
    private List<Post> postList;
    private List<Post> postSource;
    private Timer timer;
    private final OnItemClickListener onItemClicked;
    private Context context;

    public void submitList(List<Post> list) {
            mDiffer.submitList(list);

    }

    public SearchAdapter(OnItemClickListener onItemClicked, Context context) {
        this.onItemClicked = onItemClicked;
        this.context = context;
        postList = mDiffer.getCurrentList();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(
                R.layout.food_items_search
                ,parent
                ,false
        );
        return new FoodViewHolder(view,onItemClicked);

    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Post post = mDiffer.getCurrentList().get(position);
        holder.setPostList(post, context);
        holder.constrainLayout.setOnClickListener(v -> {
            onItemClicked.onItemClicked(holder.getLayoutPosition(), post);
        });

    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

     static class FoodViewHolder extends RecyclerView.ViewHolder{
        private final RoundedImageView imageView;
        private final TextView postTitle, price;
        private ConstraintLayout constrainLayout;
        public FoodViewHolder(@NonNull View itemView, OnItemClickListener homeInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.foodImageSearchTv);
            postTitle = itemView.findViewById(R.id.foodTitleSearchTv);
            price = itemView.findViewById(R.id.foodPriceSearchTv);
            constrainLayout = itemView.findViewById(R.id.constrainLayout);


        }

        void setPostList(Post post, Context context){
            Glide.with(context).load(post.getImage()).into(imageView);
            postTitle.setText(post.getTitle());
            price.setText(String.valueOf(post.getPrice()));
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
    android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
    public void searchPosts(final String searchKeyword){

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchKeyword.trim().isEmpty()){
                    mDiffer.submitList(postList);
                }else{
                    ArrayList<Post> temp = new ArrayList<>();
                    for (Post post: postList){
                        if (post.getTitle().toLowerCase().contains(searchKeyword)){
                            temp.add(post);
                            Log.d("TAG88", postList.toString());
                        }
                    }
                    mDiffer.submitList(temp);

                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        },500);
    }
    public boolean isEmptyList(){
        if (mDiffer.getCurrentList().isEmpty()){
            return true;
        }
        return false;
    }

    public void cancelTimer(){
        if (timer != null){
            timer.cancel();
        }
    }


}
