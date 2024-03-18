package com.lutech.literaryapp.sharedpreferences

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.lutech.literaryapp.utils.Constants

class SharedPreferences(context: Context) {

    private val sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    var nameAccount: String?
        get() = sharedPreferences.getString(Constants.ACCOUNT, "abc")
        set(value) = sharedPreferences.edit {
            putString(Constants.ACCOUNT, value)
        }

    var passwordAccount: String?
        get() = sharedPreferences.getString(Constants.PASSWORD, "abc")
        set(value) = sharedPreferences.edit {
            putString(Constants.PASSWORD, value)
        }

    var roleAccount: String?
        get() = sharedPreferences.getString(Constants.ROLE, "abc")
        set(value) = sharedPreferences.edit {
            putString(Constants.ROLE, value)
        }

    var isSetFullScreen : Boolean?
        get() = sharedPreferences.getBoolean(Constants.IS_LANDSCAPE, false)
        set(value) = sharedPreferences.edit {
            putBoolean(Constants.IS_LANDSCAPE, value!!)
        }
}