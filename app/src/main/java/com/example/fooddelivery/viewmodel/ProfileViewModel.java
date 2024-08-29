package com.example.fooddelivery.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fooddelivery.helper.Constants;
import com.example.fooddelivery.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<User> getUserInfo = new MutableLiveData<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private CollectionReference userReference = db.collection(Constants.USER_COLLECTION);
    public MutableLiveData<User> getGetUserInfo(){
         userReference.whereEqualTo(auth.getCurrentUser().getUid(),"id").get().addOnCompleteListener(task ->  {
             if (task.isSuccessful()){
                 for (QueryDocumentSnapshot document : task.getResult()) {
                     User user = document.toObject(User.class);
                     getUserInfo.setValue(user);
                 }
             }
         }).addOnFailureListener(e -> {

        });
         return getUserInfo;
    }
}
