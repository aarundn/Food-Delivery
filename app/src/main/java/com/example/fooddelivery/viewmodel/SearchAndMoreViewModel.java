package com.example.fooddelivery.viewmodel;

import android.util.Log;

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
import java.util.Objects;

public class SearchAndMoreViewModel extends ViewModel {
    private MutableLiveData<List<Post>> postList = new MutableLiveData<>();
    private MutableLiveData<List<Post>> searchedPost;
    private MutableLiveData<List<Post>> categoryPostList = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAllDataFetched = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isCategoryPostsFetched = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isSearchedDataFetched = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isListEmpty = new MutableLiveData<>(false);
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection(Constants.FOOD_COLLECTION_NAME);

    SearchAndMoreViewModel() {
        postList = new MutableLiveData<>(new ArrayList<>());
        searchedPost = new MutableLiveData<>(new ArrayList<>());
        categoryPostList = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Post>> getAllPostList() {
        postList = new MutableLiveData<>(new ArrayList<>());
        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot documentSnapshots = task.getResult();
                for (QueryDocumentSnapshot snapshot : documentSnapshots) {
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

    public LiveData<Boolean> getIsAllDataFetched() {
        return isAllDataFetched;
    }

    public LiveData<List<Post>> searchPosts(String keyword) {
        isSearchedDataFetched.setValue(false);
        searchedPost = new MutableLiveData<>(new ArrayList<>());
        collectionReference.orderBy(Constants.POST_TITLE).startAt(keyword).
                endAt(keyword + "~").get()
                .addOnCompleteListener(task -> {
                    List<Post> posts = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            Post post = snapshot.toObject(Post.class);
                            posts.add(post);
                            isSearchedDataFetched.setValue(true);

                        }
                    } else {
                        isSearchedDataFetched.setValue(false);
                    }
                    postList.setValue(posts);
                }).addOnFailureListener(e -> {
                    isSearchedDataFetched.setValue(false);
                });
        if (searchedPost.getValue().isEmpty()) {
            isListEmpty.setValue(true);
        } else {
            isListEmpty.setValue(false);
        }
        return searchedPost;
    }

    public MutableLiveData<List<Post>> getListByCategory(String category) {
        categoryPostList = new MutableLiveData<>(new ArrayList<>());
        collectionReference.whereEqualTo(Constants.POST_CATEGORY, category)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Post> postList = new ArrayList<>();
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            Post post = snapshot.toObject(Post.class);
                            postList.add(post);
                        }
                        categoryPostList.setValue(postList); // Set the updated list
                        isCategoryPostsFetched.setValue(true);
                    } else {
                        isCategoryPostsFetched.setValue(false);
                    }
                }).addOnFailureListener(e -> {
                    isCategoryPostsFetched.setValue(false);
                    Log.d("LIST2", e.getMessage());
                });
        return categoryPostList;
    }

    public LiveData<Boolean> getIsSearchedDataFetched() {
        return isSearchedDataFetched;
    }

    public LiveData<Boolean> getIsCategoryPostsFetched() {
        return isCategoryPostsFetched;
    }

    public LiveData<Boolean> getIsListEmpty() {
        return isListEmpty;
    }
}
