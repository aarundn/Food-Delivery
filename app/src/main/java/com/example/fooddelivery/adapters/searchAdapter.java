package com.example.fooddelivery.adapters;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fooddelivery.R;

import com.example.fooddelivery.listeners.OnItemClickListener;
import com.example.fooddelivery.models.poste;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.FoodViewHolder> {

    private List<poste> posteList;
    private List<poste> postSource;
    private Timer timer;
    private final OnItemClickListener onItemClicked;
    private Context context;

    public searchAdapter(List<poste> posteList, OnItemClickListener onItemClicked, Context context) {
        this.posteList = posteList;
        this.onItemClicked = onItemClicked;
        this.postSource = posteList;
        this.context = context;
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
        holder.setPostList(posteList.get(position));
        holder.constrainLayout.setOnClickListener(v -> {
            onItemClicked.onItemClicked(holder.getLayoutPosition(),posteList.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return posteList.size();
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

        void setPostList(poste post){
            imageView.setImageResource(post.getImage());
            postTitle.setText(post.getTitle());
            price.setText(String.valueOf(post.getPrice()));
        }
    }
    android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
    public void searchPosts(final String searchKeyword){

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchKeyword.trim().isEmpty()){
                    posteList = postSource;
                }else{
                    ArrayList<poste> temp = new ArrayList<>();
                    for (poste post: postSource){
                        if (post.getTitle().toLowerCase().contains(searchKeyword)){
                            temp.add(post);
                        }
                    }
                    posteList = temp;

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
        if (posteList.isEmpty()){
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
