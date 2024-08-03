package com.example.fooddelivery.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkReceiver extends BroadcastReceiver {

    public static ReceiverListener Listener;
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager conn =  (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        if (Listener != null) {
            boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
            Listener.onNetworkChange(isConnected);
        }
    }

    public interface ReceiverListener {
        void onNetworkChange(boolean isConnected);
    }
}
