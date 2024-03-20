package com.lutech.literaryapp.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lutech.literaryapp.screen.home.fragment.GeneralFragment
import com.lutech.literaryapp.screen.home.fragment.GradeFragment
import com.lutech.literaryapp.screen.home.fragment.TestLinkFragment
import com.lutech.literaryapp.screen.home.fragment.UserFragment

class ViewPagerAdapter(appCompatActivity : AppCompatActivity) : FragmentStateAdapter(appCompatActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GeneralFragment()
            1 -> GradeFragment()
            2 -> TestLinkFragment()
            else -> UserFragment()
        }
    }
}