package com.lutech.literaryapp.utils

import androidx.appcompat.app.AppCompatActivity
import com.valdesekamdem.library.mdtoast.MDToast

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


