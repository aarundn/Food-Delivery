package com.example.fooddelivery.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fooddelivery.helper.Constants;
import com.example.fooddelivery.models.AddToCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AddToCartViewModel extends ViewModel {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference postRf = db.collection(Constants.FOOD_COLLECTION_NAME);
    private CollectionReference  userRf = db.collection(Constants.USER_COLLECTION);
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private MutableLiveData<Boolean> isPostAddedToCart = new MutableLiveData<>();
    private MutableLiveData<List<AddToCart>> allCartPost = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAllPostGet = new MutableLiveData<>();

    public void addPostToCart(AddToCart toCart){
        DocumentReference postRf = userRf.document(auth.getCurrentUser().getUid()).collection(Constants.CART_COLLECTION_POST)
                .document(toCart.getPost().getId());
        postRf.get().addOnSuccessListener( unused -> {
            if (unused.exists()){
                isPostAddedToCart.setValue(false);
            }else {
                postRf.set(toCart);
                isPostAddedToCart.setValue(true);
            }
        }).addOnFailureListener(e -> {

        });
    }

    public LiveData<List<AddToCart>> getAllCartPost(){
        allCartPost = new MutableLiveData<>(new ArrayList<>());
        CollectionReference postRf = userRf.document(auth.getCurrentUser()
                .getUid()).collection(Constants.CART_COLLECTION_POST);
        postRf.get().addOnCompleteListener(task -> {
            List<AddToCart> allPost = new ArrayList<>();
            if (task.isSuccessful()){

                for (QueryDocumentSnapshot snapshot: task.getResult()){
                    AddToCart post = snapshot.toObject(AddToCart.class);
                    allPost.add(post);
                    isAllPostGet.setValue(true);
                }
            }
            allCartPost.setValue(allPost);
        }).addOnFailureListener(e -> {
            isAllPostGet.setValue(false);
        });
        return allCartPost;
    }

    public void updateQuantity(String documentId, int delta) {
        DocumentReference cartItemRef = userRf.document(auth.getCurrentUser().getUid())
                .collection(Constants.CART_COLLECTION_POST).document(documentId);

        db.runTransaction((Transaction.Function<Void>) transaction -> {
            AddToCart cartItem = transaction.get(cartItemRef).toObject(AddToCart.class);
            if (cartItem != null) {
                int newQuantity = cartItem.getQuantity() + delta;
                if (newQuantity > 0) {  // Ensure quantity doesn't go below 1
                    transaction.update(cartItemRef, "quantity", newQuantity);
                }
            }
            return null;
        }).addOnSuccessListener(aVoid -> {
            // Transaction successful, handle success (e.g., notify observers)
        }).addOnFailureListener(e -> {
            // Handle transaction failure
        });
    }

    public LiveData<Boolean> getIsPostAddedToCart(){
        return isPostAddedToCart;
    }
    public LiveData<Boolean> getIsAllPostGet(){
        return isAllPostGet;
    }
}
