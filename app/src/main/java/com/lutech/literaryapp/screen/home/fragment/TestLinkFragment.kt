package com.lutech.literaryapp.screen.home.fragment

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.lutech.literaryapp.model.LinkTest
import com.lutech.literaryapp.screen.home.adapter.LinkTestAdapter
import com.lutech.literaryapp.utils.invisible
import com.lutech.literaryapp.utils.visible
import kotlinx.android.synthetic.main.fragment_test_link.layoutEmptyListLink
import kotlinx.android.synthetic.main.fragment_test_link.layoutLoadingDataTest
import kotlinx.android.synthetic.main.fragment_test_link.rcvListLinkTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestLinkFragment : Fragment() {

    private var mContext : Context? = null

    private var mListLinkTest: ArrayList<LinkTest> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = requireContext()
        return inflater.inflate(R.layout.fragment_test_link, container, false)
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
        getListAllDocumentsData()
    }

    private fun handleEvent() {

    }

    private fun getListAllDocumentsData() {
        val databaseListProject = FirebaseDatabase.getInstance().getReference("baithitracnghiem")
        databaseListProject
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        lifecycleScope.launch(Dispatchers.Main) {
                            visible(layoutLoadingDataTest)
                            if (mListLinkTest.isNotEmpty()) {
                                mListLinkTest.clear()
                            }
                            withContext(Dispatchers.IO) {
                                for (userSnapshot in snapshot.children) {
                                    mListLinkTest.add(userSnapshot.getValue(LinkTest::class.java)!!)
                                }
                                mListLinkTest.reverse()
                            }
                            invisible(layoutLoadingDataTest)
                            layoutEmptyListLink.isVisible = mListLinkTest.isEmpty()
                            rcvListLinkTest.adapter =
                                LinkTestAdapter(mContext!!, mListLinkTest,object : LinkTestAdapter.OnItemLinkTestClickListener {
                                    override fun onItemDocumentPosClick(position: Int) {
                                        try {
                                            val uri = Uri.parse(mListLinkTest[position].pathLinkTest) // missing 'http://' will cause crashed
                                            startActivity(Intent(Intent.ACTION_VIEW, uri))
                                        } catch (e: ActivityNotFoundException) {
                                            e.printStackTrace()
                                        }
                                    }
                                })
                        }
                    } else {
                        layoutEmptyListLink.isVisible = true
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