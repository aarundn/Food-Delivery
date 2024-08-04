package com.example.fooddelivery.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fooddelivery.helper.Constants;
import com.example.fooddelivery.models.Post;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchAndMoreViewModel extends ViewModel {
    private MutableLiveData<List<Post>> postList = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAllDataFetched = new MutableLiveData<>(false);
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection(Constants.FOOD_COLLECTION_NAME);
    SearchAndMoreViewModel(){
        postList = new MutableLiveData<>(new ArrayList<>());
    }

    public MutableLiveData<List<Post>> getAllPostList(){
        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                QuerySnapshot documentSnapshots = task.getResult();
                for (QueryDocumentSnapshot snapshot: documentSnapshots){
                    Post post = snapshot.toObject(Post.class);
                    postList.getValue().add(post);
                    isAllDataFetched.setValue(true);
                }
            }
        }).addOnFailureListener(e -> {
            isAllDataFetched.setValue(false);
        });
        return postList;
    }
    public LiveData<Boolean> getIsAllDataFetched(){
        return isAllDataFetched;
    }
}
