package com.lutech.literaryapp.screen.home.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lutech.literaryapp.R
import com.lutech.literaryapp.screen.grade.GradeActivity
import com.lutech.literaryapp.utils.Constants
import kotlinx.android.synthetic.main.fragment_grade.clGradeEleven
import kotlinx.android.synthetic.main.fragment_grade.clGradeTen
import kotlinx.android.synthetic.main.fragment_grade.clGradeTwelve

class GradeFragment : Fragment() {

    private var mContext : Context? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = requireContext()
        return inflater.inflate(R.layout.fragment_grade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        handleEvent()
    }

    private fun initData() {

    }

    private fun initView() {

    }

    private fun handleEvent() {
        clGradeTen.setOnClickListener {
            startActivity(Intent(mContext, GradeActivity::class.java).apply {
                putExtra(Constants.GRADE, "khoi10")
            })
        }

        clGradeEleven.setOnClickListener {
            startActivity(Intent(mContext, GradeActivity::class.java).apply {
                putExtra(Constants.GRADE, "khoi11")
            })
        }

        clGradeTwelve.setOnClickListener {
            startActivity(Intent(mContext, GradeActivity::class.java).apply {
                putExtra(Constants.GRADE, "khoi12")
            })
        }
    }

}