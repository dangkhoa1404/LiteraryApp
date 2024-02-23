package com.lutech.literaryapp.base

import android.app.Dialog
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.lutech.literaryapp.R
import com.lutech.literaryapp.receiver.NetworkChangeReceiver
import com.lutech.literaryapp.utils.Utils

abstract class BaseActivity : AppCompatActivity(), NetworkChangeReceiver.NetworkStateListener {

    private var mNetworkChangeReceiver: NetworkChangeReceiver? = null

    private var mNoInternetDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.color_white)
        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.color_white)

        initDatabaseActivity()
        initViewBaseActivity()
    }

    private fun initViewBaseActivity() {
        mNoInternetDialog = Utils.onCreateDialog(this, R.layout.dialog_no_network, false)
    }

    private fun initDatabaseActivity() {
        mNetworkChangeReceiver =
            NetworkChangeReceiver(this)
        registerReceiver(mNetworkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onInternetAvailable() {
        if (mNoInternetDialog != null) {
            mNoInternetDialog!!.dismiss()
        }
    }

    override fun onOffline() {
        if (mNoInternetDialog != null) {

//            mNoInternetDialog!!.tvRetry.setOnClickListener {
//                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS));
//            }

            mNoInternetDialog!!.show()
        }
    }

    override fun onDestroy() {
        unregisterReceiver(mNetworkChangeReceiver)
        super.onDestroy()
    }

}