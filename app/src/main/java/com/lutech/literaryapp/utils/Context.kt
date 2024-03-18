package com.lutech.literaryapp.utils

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lutech.literaryapp.sharedpreferences.SharedPreferences
import com.valdesekamdem.library.mdtoast.MDToast


val Context.sharedPreference: SharedPreferences
    get() = SharedPreferences(this)

fun AppCompatActivity.successToast(msg: String) {
    MDToast.makeText(this, msg, MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show()
}

fun AppCompatActivity.errorToast(msg: String) {
    MDToast.makeText(this, msg, MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show()
}

fun AppCompatActivity.warningToast(msg: String) {
    MDToast.makeText(this, msg, MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING).show()
}

fun AppCompatActivity.infoToast(msg: String) {
    MDToast.makeText(this, msg, MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show()
}

fun visible(view: View) {
    view.visibility = View.VISIBLE
}

fun invisible(view: View) {
    view.visibility = View.INVISIBLE
}

fun gone(view: View) {
    view.visibility = View.GONE
}

