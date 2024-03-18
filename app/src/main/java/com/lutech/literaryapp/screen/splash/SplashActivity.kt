package com.lutech.literaryapp.screen.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.lutech.literaryapp.R
import com.lutech.literaryapp.base.BaseActivity
import com.lutech.literaryapp.screen.home.activity.HomeActivity
import com.lutech.literaryapp.screen.signin.SignInActivity
import com.lutech.literaryapp.utils.sharedPreference
import kotlinx.android.synthetic.main.activity_splash.imgIconApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : BaseActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initData()
        initView()
    }

    private fun initData() {
        Log.d("===>0249824", "nameAccount: " + sharedPreference.nameAccount!!)
        if(sharedPreference.nameAccount!!.uppercase().trim().contains("GV")) {
            database = FirebaseDatabase.getInstance().getReference("taikhoan/GV")
        } else {
            database = FirebaseDatabase.getInstance().getReference("taikhoan/HS")
        }

    }

    private fun initView() {
        imgIconApp.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))

        database.child(sharedPreference.nameAccount!!.trim()).get().addOnSuccessListener {
            if (it.exists()) {
                lifecycleScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO) {
                        sharedPreference.apply {
                            nameAccount = it.child("idNguoidung").value.toString().uppercase()
                            passwordAccount = it.child("matKhau").value.toString()
                            roleAccount = it.child("idChucVu").value.toString()
                        }
                    }
                }
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Log.d("===>20492042", "not exist: ")
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Tìm tài khoản thất bại", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        imgIconApp.clearAnimation()
    }
}