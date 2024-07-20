package com.example.fooddelivery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fooddelivery.R;
import com.example.fooddelivery.listeners.HomeViewpagerOnClickListener;
import com.example.fooddelivery.models.poste;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class foodAdapter extends RecyclerView.Adapter<foodAdapter.FoodViewHolder> {

    private List<poste> posteList;

    private final HomeViewpagerOnClickListener homeInterface;


    public foodAdapter(List<poste> posteList, HomeViewpagerOnClickListener homeInterface) {
        this.posteList = posteList;
        this.homeInterface = homeInterface;
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
        holder.setPostList(posteList.get(position));

    }

    @Override
    public int getItemCount() {
        return posteList.size();
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

        void setPostList(poste post){
            imageView.setImageResource(post.getImage());
            postTitle.setText(post.getTitle());
            price.setText(String.valueOf(post.getPrice()));
        }
    }



}
