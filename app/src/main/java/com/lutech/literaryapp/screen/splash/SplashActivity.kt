package com.lutech.literaryapp.screen.splash

import android.os.Bundle
import android.view.animation.AnimationUtils
import com.lutech.literaryapp.R
import com.lutech.literaryapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.imgIconApp

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }

    private fun initView() {
        imgIconApp.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
    }

    override fun onDestroy() {
        super.onDestroy()
        imgIconApp.clearAnimation()
    }
}