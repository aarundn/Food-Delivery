package com.example.fooddelivery.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fooddelivery.helper.Constants;
import com.example.fooddelivery.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<User> getUserInfo = new MutableLiveData<>();
    private MutableLiveData<Boolean> isUserAdded = new MutableLiveData<>();
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

    public void modifyUserInfo(User user){


        Map<String, Object> updates = new HashMap<>();

        // Assuming User has fields like 'name', 'email', and 'phone'
        if (user.getUserName() != null) {
            updates.put("userName", user.getUserName());
        }
//        if (user.getEmail() != null) {
//            updates.put("email", user.getEmail());
//        }
//        if (user.getPhone() != null) {
//            updates.put("phone", user.getPhone());
//        }

        // Check if there are updates to be made
        if (!updates.isEmpty()) {
            userReference.document(user.getId())
                    .update(updates)
                    .addOnSuccessListener(unused -> isUserAdded.setValue(true))
                    .addOnFailureListener(e -> isUserAdded.setValue(false));
        } else {
            // No fields to update
            isUserAdded.setValue(false);
        }
    }

    public LiveData<Boolean> getIsUserAdded(){
        return isUserAdded;
    }
}
