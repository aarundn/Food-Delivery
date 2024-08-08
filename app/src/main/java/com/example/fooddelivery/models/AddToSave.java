package com.example.fooddelivery.models;

public class AddToSave {
    private Post post;

    public AddToSave(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
