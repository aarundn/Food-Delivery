package com.example.fooddelivery.viewmodel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fooddelivery.helper.Constants;
import com.example.fooddelivery.models.User;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<User> _getUserInfo = new MutableLiveData<>();
    public LiveData<User> getUserInfo = _getUserInfo;
    private MutableLiveData<Boolean> isUserAdded = new MutableLiveData<>();
    private MutableLiveData<String> _downloadImagePath = new MutableLiveData<>();
    private MutableLiveData<Boolean> isProfileImageAdded = new MutableLiveData<>(false);
    public MutableLiveData<String> downloadImagePath = _downloadImagePath;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private CollectionReference userReference = db.collection(Constants.USER_COLLECTION);
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    public void getGetUserInfo(){
         userReference.document(Objects.requireNonNull(auth.getCurrentUser()).getUid()).get().addOnSuccessListener(task ->  {

                     User user = task.toObject(User.class);
                     _getUserInfo.setValue(user);

         }).addOnFailureListener(e -> {

        });
    }

    public void addImageToFireBaseStorage(Uri imageUri){
        String imageId = UUID.randomUUID().toString();
        StorageReference imageRef = reference.child("Files/profile/"+imageId);
        imageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                downloadImagePath.setValue(uri.toString());
                isProfileImageAdded.setValue(true);
            }).addOnFailureListener( e -> {

            });
        }).addOnFailureListener( e -> {

        });
    }

    public void modifyUserInfo(User user){

        Map<String, Object> updates = new HashMap<>();
        if (user.getAddress() != null) {
            updates.put("address", user.getAddress());
        }
        if (user.getPhoneNumber() != null) {
            updates.put("phoneNumber", user.getPhoneNumber());
        }
        if (user.getUserName() != null) {
            updates.put("userName", user.getUserName());
        }
        if (user.getImagePath() != null) {
            updates.put("imagePath", user.getImagePath());
        }

        // Check if there are updates to be made
            userReference.document(auth.getCurrentUser().getUid())
                    .update(updates)
                    .addOnSuccessListener(unused -> isUserAdded.setValue(true))
                    .addOnFailureListener(e -> isUserAdded.setValue(false));

            // No fields to update
            isUserAdded.setValue(false);

    }

    public LiveData<Boolean> getIsUserAdded(){
        return isUserAdded;
    }
    public LiveData<Boolean> getIsImageProfileAdded(){
        return isProfileImageAdded;
    }
}
