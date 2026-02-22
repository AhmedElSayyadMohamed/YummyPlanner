package com.example.yummyplanner.utiles;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class NetworkUtils {

    private static final MutableLiveData<Boolean> isNetworkAvailable = new MutableLiveData<>();

    public static LiveData<Boolean> isNetworkAvailable() {
        return isNetworkAvailable;
    }

    public static void registerNetworkCallback(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        connectivityManager.registerNetworkCallback(networkRequest, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                isNetworkAvailable.postValue(true);
            }

            @Override
            public void onLost(Network network) {
                isNetworkAvailable.postValue(false);
            }

            @Override
            public void onUnavailable() {
                isNetworkAvailable.postValue(false);
            }
        });

        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        boolean isConnected = capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        isNetworkAvailable.postValue(isConnected);
    }
}