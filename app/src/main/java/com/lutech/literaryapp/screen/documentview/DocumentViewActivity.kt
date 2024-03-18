package com.lutech.literaryapp.screen.documentview

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.lutech.literaryapp.R
import com.lutech.literaryapp.base.BaseActivity
import com.lutech.literaryapp.utils.Constants
import com.lutech.literaryapp.utils.gone
import com.lutech.literaryapp.utils.visible
import kotlinx.android.synthetic.main.activity_document_view.ivBack
import kotlinx.android.synthetic.main.activity_document_view.layoutUploadFilesFromServer
import kotlinx.android.synthetic.main.activity_document_view.tvNameFileSaved
import kotlinx.android.synthetic.main.activity_document_view.viewPDF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class DocumentViewActivity : BaseActivity() {

    private var fileName : String = ""

    private var filePath : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_view)
        initData()
        initView()
        handleEvent()
    }

    private fun initData() {
        fileName = intent.getStringExtra(Constants.KEY_FILE_NAME)!!
        filePath = intent.getStringExtra(Constants.KEY_FILE_PATH)!!
    }

    private fun initView() {
        visible(layoutUploadFilesFromServer)
        tvNameFileSaved.requestFocus()
        tvNameFileSaved.text = fileName

        lifecycleScope.launch(Dispatchers.IO) {
            val inputStream = URL(filePath).openStream()
            withContext(Dispatchers.Main) {
                viewPDF.fromStream(inputStream).onRender { pages, pageWidth, pageHeight ->
                    if (pages >= 1) {
                        gone(layoutUploadFilesFromServer)
                    }
                }.load()
            }
        }
    }

    private fun handleEvent() {
        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroy() {
        lifecycleScope.cancel()
        super.onDestroy()
    }
}