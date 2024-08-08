package com.example.fooddelivery.models;

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
}
