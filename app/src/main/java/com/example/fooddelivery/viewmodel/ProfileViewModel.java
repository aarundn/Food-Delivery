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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<User> _getUserInfo = new MutableLiveData<>();
    public LiveData<User> getUserInfo = _getUserInfo;
    private MutableLiveData<Boolean> isUserAdded = new MutableLiveData<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private CollectionReference userReference = db.collection(Constants.USER_COLLECTION);
    public void getGetUserInfo(){
         userReference.document(Objects.requireNonNull(auth.getCurrentUser()).getUid()).get().addOnSuccessListener(task ->  {

                     User user = task.toObject(User.class);
                     _getUserInfo.setValue(user);

         }).addOnFailureListener(e -> {

        });
    }

    public void modifyUserInfo(User user){



//        if (user.getEmail() != null) {
//            updates.put("email", user.getEmail());
//        }
//        if (user.getPhone() != null) {
//            updates.put("phone", user.getPhone());
//        }

        // Check if there are updates to be made
            userReference.document(auth.getCurrentUser().getUid())
                    .update("userName",user.getUserName())
                    .addOnSuccessListener(unused -> isUserAdded.setValue(true))
                    .addOnFailureListener(e -> isUserAdded.setValue(false));

            // No fields to update
            isUserAdded.setValue(false);

    }

    public LiveData<Boolean> getIsUserAdded(){
        return isUserAdded;
    }
}
