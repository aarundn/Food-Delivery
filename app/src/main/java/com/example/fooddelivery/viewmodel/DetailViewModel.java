package com.example.fooddelivery.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fooddelivery.helper.Constants;
import com.example.fooddelivery.models.AddToCart;
import com.example.fooddelivery.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DetailViewModel extends ViewModel {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference  postRf = db.collection(Constants.FOOD_COLLECTION_NAME);
    private CollectionReference  userRf = db.collection(Constants.USER_COLLECTION);
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private MutableLiveData<Boolean> isPostSaved = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isPostExist = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isPostUnsaved = new MutableLiveData<>(false);
    MutableLiveData<List<AddToCart>> savedPostList = new MutableLiveData<>();



    public void addPostToCart(AddToCart postCart){
        DocumentReference postRf = userRf.document(auth.getCurrentUser().getUid()).collection(Constants.CART_COLLECTION_POST)
                .document(postCart.getPost().getId());
        postRf.get().addOnSuccessListener( unused -> {
                    if (unused.exists()){
                        postRf.delete();
                        isPostSaved.setValue(false);
                    }else {
                        postRf.set(postCart);
                        isPostSaved.setValue(true);
                    }
                }).addOnFailureListener(e -> {

                });
    }
    public void getSavedPost(AddToCart addToCart){
        DocumentReference postRf = userRf.document(auth.getCurrentUser().getUid()).collection(Constants.CART_COLLECTION_POST)
                .document(addToCart.getPost().getId());
        postRf.get().addOnSuccessListener( documentSnapshot -> {
            if (documentSnapshot.exists()){
                isPostExist.setValue(true);
            }else {
                isPostExist.setValue(false);
            }
        }).addOnFailureListener(e -> {

        });
    }

    public LiveData<List<AddToCart>> getAllSavedPost(){
        savedPostList = new MutableLiveData<>(new ArrayList<>());
        CollectionReference postRf = userRf.document(auth.getCurrentUser()
                        .getUid()).collection(Constants.CART_COLLECTION_POST);
        postRf.get().addOnCompleteListener(task -> {
                List<AddToCart> allPost = new ArrayList<>();
            if (task.isSuccessful()){

                for (QueryDocumentSnapshot snapshot: task.getResult()){
                    AddToCart post = snapshot.toObject(AddToCart.class);
                    allPost.add(post);
                }
            }
            savedPostList.setValue(allPost);
        }).addOnFailureListener(e -> {

        });
        return savedPostList;
    }
    public void removePost(AddToCart addToCart){
        DocumentReference postRf =
                userRf.document(auth.getCurrentUser().getUid()).collection(Constants.CART_COLLECTION_POST)
                        .document(addToCart.getPost().getId());
        postRf.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()){
                postRf.delete();
                isPostUnsaved.setValue(true);
            }
        }).addOnFailureListener(e -> {

        });

    }

    public LiveData<Boolean> getIsPostSaved(){
        return isPostSaved;
    }

    public LiveData<Boolean> getIsPostUnsaved() {
        return isPostUnsaved;
    }

    public LiveData<Boolean> getIsPostExist() {
        return isPostExist;
    }
}
