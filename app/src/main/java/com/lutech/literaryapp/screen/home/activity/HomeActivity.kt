package com.lutech.literaryapp.screen.home.activity

import android.os.Bundle
import com.lutech.literaryapp.adapter.ViewPagerAdapter
import com.google.android.material.navigation.NavigationBarView
import com.lutech.literaryapp.R
import com.lutech.literaryapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.bottom_navigation
import kotlinx.android.synthetic.main.activity_main.viewPager2

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initView()
        handleEvent()
    }

    private fun initData() {}

    private fun initView() {
        viewPager2.apply {
            isUserInputEnabled = false
            adapter = ViewPagerAdapter(this@HomeActivity)
        }
    }

    private fun handleEvent() {
        bottom_navigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.it_drama -> {
                    viewPager2.setCurrentItem(0, false)
                    return@OnItemSelectedListener true
                }

                R.id.it_grade -> {
                    viewPager2.setCurrentItem(1, false)
                    return@OnItemSelectedListener true
                }

                R.id.it_link_test -> {
                    viewPager2.setCurrentItem(2, false)
                    return@OnItemSelectedListener true
                }

                R.id.it_individual -> {
                    viewPager2.setCurrentItem(3, false)
                    return@OnItemSelectedListener true
                }
            }
            false
        })
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}