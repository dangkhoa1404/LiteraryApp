package com.lutech.literaryapp.screen.grade

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lutech.literaryapp.R
import com.lutech.literaryapp.base.BaseActivity
import com.lutech.literaryapp.model.LocalDocument
import com.lutech.literaryapp.screen.documentview.DocumentViewActivity
import com.lutech.literaryapp.screen.home.adapter.LocalDocumentAdapter
import com.lutech.literaryapp.screen.videoview.VideoViewActivity
import com.lutech.literaryapp.utils.Constants
import com.lutech.literaryapp.utils.invisible
import com.lutech.literaryapp.utils.visible
import kotlinx.android.synthetic.main.activity_grade.ivBack
import kotlinx.android.synthetic.main.activity_grade.layoutEmptyListData
import kotlinx.android.synthetic.main.activity_grade.layoutLoadingData
import kotlinx.android.synthetic.main.activity_grade.rcvListDataByGrade
import kotlinx.android.synthetic.main.activity_grade.tvNameGrade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GradeActivity : BaseActivity() {

    private var mGrade : String = ""

    private var mListDocument: ArrayList<LocalDocument> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade)
        initData()
        initView()
        handleEvent()
    }

    private fun initData() {
        mGrade = intent.getStringExtra(Constants.GRADE)!!
    }

    private fun initView() {
        when(mGrade) {
            "khoi10" -> {
                tvNameGrade.text = getString(R.string.txt_grade_10)
            }
            "khoi11" -> {
                tvNameGrade.text = getString(R.string.txt_grade_11)
            }
            "khoi12" -> {
                tvNameGrade.text = getString(R.string.txt_grade_12)
            }
        }
        getListAllDocumentsData(mGrade)

    }

    private fun handleEvent() {
        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun getListAllDocumentsData(grade : String) {
        val databaseListProject = FirebaseDatabase.getInstance().getReference("tailieu/${grade}")
        databaseListProject
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        lifecycleScope.launch(Dispatchers.Main) {
                            visible(layoutLoadingData)
                            if (mListDocument.isNotEmpty()) {
                                mListDocument.clear()
                            }
                            withContext(Dispatchers.IO) {
                                for (userSnapshot in snapshot.children) {
                                    mListDocument.add(userSnapshot.getValue(LocalDocument::class.java)!!)
                                }
                                mListDocument.reverse()
                            }
                            invisible(layoutLoadingData)
                            layoutEmptyListData.isVisible = mListDocument.isEmpty()
                            rcvListDataByGrade.adapter =
                                LocalDocumentAdapter(this@GradeActivity, mListDocument, object : LocalDocumentAdapter.OnItemDocumentClickListener {
                                    override fun onItemDocumentPosClick(position: Int) {
                                        val mIntent = Intent(this@GradeActivity, if(mListDocument[position].typeDocument == "VANBAN") {
                                            DocumentViewActivity::class.java
                                        } else {
                                            VideoViewActivity::class.java
                                        })

                                        startActivity(mIntent.apply {
                                            putExtra(Constants.KEY_FILE_NAME, mListDocument[position].nameDocument)
                                            putExtra(Constants.KEY_FILE_PATH, mListDocument[position].pathDocument)
                                        })
                                    }
                                })
                        }
                    } else {
                        layoutEmptyListData.isVisible = true
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    override fun onDestroy() {
        lifecycleScope.cancel()
        super.onDestroy()
    }

}