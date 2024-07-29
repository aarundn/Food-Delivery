package com.example.fooddelivery.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fooddelivery.helper.Constants;
import com.example.fooddelivery.models.Post;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeViewModel extends ViewModel {
    MutableLiveData<List<Post>> postList1;
    CollectionReference collectionReference;
    private final MutableLiveData<Boolean> _isInfoFetched = new MutableLiveData<>(false);

    FirebaseFirestore db;
    public HomeViewModel() {
        postList1 = new MutableLiveData<>(new ArrayList<>());
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection(Constants.FOOD_COLLECTION_NAME);
    }


    public MutableLiveData<List<Post>> getHomePosts(String tabName){
        postList1 = new MutableLiveData<>(new ArrayList<>());
        collectionReference.whereEqualTo(Constants.POST_CATEGORY,tabName).limit(3).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                QuerySnapshot documentSnapshot = task.getResult();
                if (documentSnapshot != null){
                    for (QueryDocumentSnapshot snapshot:documentSnapshot) {
                        Post Post = snapshot.toObject(Post.class);
                        postList1.getValue().add(Post);
                        _isInfoFetched.setValue(true);
                    }
                    postList1.setValue(postList1.getValue());
                }
            }
        }).addOnFailureListener(e -> {
            Log.d("ERROR:", Objects.requireNonNull(e.getMessage()));
        });
        return postList1;
    }
    public LiveData<Boolean> getIsInfoFetched() {
        return _isInfoFetched;
    }
}
