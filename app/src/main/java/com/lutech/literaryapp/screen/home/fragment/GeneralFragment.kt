package com.lutech.literaryapp.screen.home.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lutech.literaryapp.R
import com.lutech.literaryapp.model.LocalDocument
import com.lutech.literaryapp.screen.documentview.DocumentViewActivity
import com.lutech.literaryapp.screen.home.adapter.LocalDocumentAdapter
import com.lutech.literaryapp.screen.videoview.VideoViewActivity
import com.lutech.literaryapp.utils.Constants
import com.lutech.literaryapp.utils.invisible
import com.lutech.literaryapp.utils.visible
import kotlinx.android.synthetic.main.fragment_general.layoutEmptyListProject
import kotlinx.android.synthetic.main.fragment_general.layoutLoadingData
import kotlinx.android.synthetic.main.fragment_general.rcvListGeneralTheory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GeneralFragment : Fragment() {

    private var mContext: Context? = null

    private var mListDocument: ArrayList<LocalDocument> = arrayListOf()

    override fun onAttach(context: Context) {
        mContext = requireContext()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        handleEvent()
    }

    private fun initData() {}

    private fun initView() {
        getListAllDocumentsData()
    }

    private fun handleEvent() {}

    private fun getListAllDocumentsData() {
        val databaseListProject = FirebaseDatabase.getInstance().getReference("tailieu/chung")
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
                            layoutEmptyListProject.isVisible = mListDocument.isEmpty()
                            rcvListGeneralTheory.adapter =
                                LocalDocumentAdapter(mContext!!, mListDocument, object : LocalDocumentAdapter.OnItemDocumentClickListener {
                                    override fun onItemDocumentPosClick(position: Int) {
                                        val mIntent = Intent(mContext, if(mListDocument[position].typeDocument == "VANBAN") {
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
                        layoutEmptyListProject.isVisible = true
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