package com.doan.doanapplication.screen.addfiles.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doan.doanapplication.interfaces.OnItemPositionClickListener
import com.lutech.literaryapp.model.LocalFile
import com.lutech.literaryapp.R
import kotlinx.android.synthetic.main.item_list_video.view.ivDeletePosFile
import kotlinx.android.synthetic.main.item_list_video.view.tvFileName
import kotlin.collections.ArrayList

class ChooseFileAdapter(
    val mContext: Context,
    private var mListFiles: ArrayList<LocalFile>,
    private val onItemPosClick: OnItemPositionClickListener
) : RecyclerView.Adapter<ChooseFileAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_video, parent, false)
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentFile = mListFiles[position]

        holder.itemView.apply {

            tvFileName.text = currentFile.fileName

            ivDeletePosFile.setOnClickListener {
                removeItem(position)
                onItemPosClick.onItemPosOptionClick(position)
            }

            setOnClickListener {
                onItemPosClick.onItemPosClick(position)
            }
        }
    }

    private fun removeItem(position: Int){
        mListFiles.removeAt(position)
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int = mListFiles.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}