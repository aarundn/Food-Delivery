package com.example.fooddelivery.models;

import androidx.annotation.Nullable;

public class AddToCart {
    private Post post;
    private String Id;
    private int quantity;

    public AddToCart(Post post, int quantity) {
        this.post = post;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public AddToCart() {
    }
}
