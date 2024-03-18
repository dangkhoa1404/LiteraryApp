package com.lutech.literaryapp.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.SystemClock
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lutech.literaryapp.BuildConfig
import com.lutech.literaryapp.R
import java.io.File

object Utils {

    private var mLastClickTime: Long = 0L

    fun onCreateDialog(
        context: Context,
        layout: Int,
        isCanceledOnTouchOutside: Boolean = false
    ): Dialog {
        val dialog = Dialog(context)
        dialog.setContentView(layout)
        dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
        )
        return dialog
    }

    fun onCreateBottomSheetDialog(
        context: Context,
        dialog_update_version: Int,
        isCanceledOnTouchOutside: Boolean = true
    ): Dialog {
        val dialogRate =
            BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
        dialogRate.setContentView(dialog_update_version)
        dialogRate.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
        dialogRate.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogRate.window!!.setGravity(Gravity.BOTTOM)
        dialogRate.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialogRate
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(connectivityManager == null) return true
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.state == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.state == NetworkInfo.State.CONNECTED
    }

    fun isClickRecently(delayTime: Long = 1000): Boolean {
        if (SystemClock.elapsedRealtime() - mLastClickTime < delayTime) {
            return true
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return false
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}