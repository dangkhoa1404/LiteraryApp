package com.lutech.literaryapp.screen.uploadvideo

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.documentfile.provider.DocumentFile
import com.lutech.literaryapp.model.LocalFile
import com.lutech.literaryapp.R
import com.lutech.literaryapp.base.BaseActivity
import com.lutech.literaryapp.utils.Constants
import kotlinx.android.synthetic.main.activity_upload_video.layoutEmptyListFilesUpload
import kotlinx.android.synthetic.main.activity_upload_video.rcvChooseFile
import java.io.File

class UploadVideoActivity : BaseActivity() {

    private var fileUri: Uri? = null

    private var fileName: String? = null

    private var mListFile: ArrayList<LocalFile> = arrayListOf()

    private var myLauncherToDrive: ActivityResultLauncher<String>? = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        fileUri = uri
        fileName = uri?.let { DocumentFile.fromSingleUri(this, it)?.name }
        if (fileName != null) {
            mListFile.add(LocalFile("", fileName, fileUri.toString()))
            layoutEmptyListFilesUpload.isVisible = mListFile.isEmpty()
            rcvChooseFile.adapter!!.notifyDataSetChanged()
        }
    }

    private var startActivityResult = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Constants.IMAGE_FROM_GALLERY) {
            fileUri = Uri.fromFile(File(Constants.PATH_IMAGE_FROM_GALLERY))
            mListFile.add(LocalFile("", Constants.PATH_IMAGE_FROM_GALLERY.substringAfterLast("/"), fileUri.toString()))
            layoutEmptyListFilesUpload.isVisible = mListFile.isEmpty()
            rcvChooseFile.adapter!!.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_video)
        initData()
        initView()
        handleEvent()
    }

    private fun initData() {

    }

    private fun initView() {

    }

    private fun handleEvent() {

    }
}