package com.lutech.literaryapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import com.lutech.literaryapp.utils.Utils;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private NetworkStateListener mNetworkStateListener;

    public NetworkChangeReceiver(NetworkStateListener networkStateListener) {
        mNetworkStateListener = networkStateListener;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        try {
            if (context != null && intent != null) {
                final String action = intent.getAction();
                switch (action) {
                    case ConnectivityManager.CONNECTIVITY_ACTION:
                        if (Utils.INSTANCE.isInternetAvailable(context)) {
                            if (mNetworkStateListener != null) {
                                mNetworkStateListener.onInternetAvailable();
                            }
                            intent.setAction("ok");
                        } else {
                            intent.setAction("fail");
                            if (mNetworkStateListener != null) {
                                mNetworkStateListener.onOffline();
                            }
                            Log.d("1223232323", "fail");
                        }

                        break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface NetworkStateListener {
        void onInternetAvailable();

        void onOffline();
    }
}