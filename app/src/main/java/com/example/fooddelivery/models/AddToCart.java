package com.example.fooddelivery.models;

import androidx.annotation.Nullable;

public class AddToCart {
    private Post post;

    public AddToCart(Post post) {
        this.post = post;
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
