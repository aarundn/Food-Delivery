package com.example.fooddelivery.entities;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fooddelivery.R;
import com.example.fooddelivery.fragments.CardFragment;
import com.example.fooddelivery.fragments.FavoriteFragment;
import com.example.fooddelivery.fragments.HomeFragment;
import com.example.fooddelivery.fragments.profileFragment;
import com.example.fooddelivery.helper.NetworkReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements NetworkReceiver.ReceiverListener {

    private BottomNavigationView bottomNavigationView;
    private LinearLayout checkNetworkStatLayout;
    private FrameLayout contentLayout;
    private Button tryAgainButton;
    private ProgressBar tryAgainProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottonNav);
        checkNetworkStatLayout = findViewById(R.id.networkStatLayout);
        contentLayout = findViewById(R.id.frameLayout);
        tryAgainButton = findViewById(R.id.tryAgainButton);
        tryAgainProgressBar = findViewById(R.id.tryAgainProgressBar);

        checkNetwork();
        tryAgainButton.setOnClickListener(v -> {
            tryAgainProgressBar.setVisibility(View.VISIBLE);
            tryAgainButton.setText("");
            checkNetworkForOthers();

        });

//        setPostViewPager();
        replaceFragment(new HomeFragment());
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    checkNetwork();
                    break;
                case R.id.heart:
                    replaceFragment(new FavoriteFragment());
                    checkNetworkForOthers();
                    break;
                case R.id.user:
                    replaceFragment(new profileFragment());
                    checkNetworkForOthers();
                    break;
                case R.id.history:
                    replaceFragment(new CardFragment());
                    checkNetworkForOthers();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            return true;
        });

//        setRecyclerView();
    }
//    private void setRecyclerView(){
//        RecyclerView recyclerView = findViewById(R.id.mainPager);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
//                LinearLayoutManager.HORIZONTAL,false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(new foodAdapter(getPosts()));
//    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        onNetworkChange(isConnected);
    }


    private void checkNetwork() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");
        registerReceiver(new NetworkReceiver(), intentFilter);
        NetworkReceiver.Listener = this;

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
//        displayNetworkState(isConnected);
        showSnackBar(isConnected);
    }

    private void showSnackBar(boolean isConnected) {

        String message;
        int color;

        if (isConnected) {
            message = "Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Not Connected to Internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar.make(findViewById(R.id.bottonNav), message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.BLACK);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER_VERTICAL;

        View view = snackbar.getView();
        view.setLayoutParams(params);
        view.setPadding(16, 16, 16, 16);

        TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
        view.setBackgroundColor(Color.BLACK);
        textView.setTextColor(color);

        if (!isConnected) {
            snackbar.show();
        }
    }
        private void checkNetworkForOthers() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");
            registerReceiver(new NetworkReceiver(), intentFilter);
            NetworkReceiver.Listener = this;

            ConnectivityManager manager = (ConnectivityManager) getApplicationContext().
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
            displayNetworkState(isConnected);

        }





    private void displayNetworkState(boolean isConnected) {
        tryAgainProgressBar.setVisibility(View.GONE);
        tryAgainButton.setText("Try again");
        if (!isConnected){
            contentLayout.setVisibility(View.GONE);
            checkNetworkStatLayout.setVisibility(View.VISIBLE);
        } else {
            contentLayout.setVisibility(View.VISIBLE);
            checkNetworkStatLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // call method
        checkNetwork();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // call method
        checkNetwork();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkNetwork();
    }
}