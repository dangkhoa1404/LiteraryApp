package com.lutech.literaryapp.screen.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lutech.literaryapp.R
import com.lutech.literaryapp.model.LocalDocument
import kotlinx.android.synthetic.main.item_list_file.view.tvFileName
import java.io.PipedOutputStream
import kotlin.collections.ArrayList

class LocalDocumentAdapter(
    val mContext: Context,
    private var mListDocument: ArrayList<LocalDocument>,
    private val onItemPosClick: OnItemDocumentClickListener
) : RecyclerView.Adapter<LocalDocumentAdapter.MyViewHolder>() {

    interface OnItemDocumentClickListener {
        fun onItemDocumentPosClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_file, parent, false)
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentDocument = mListDocument[position]

        holder.itemView.apply {
            tvFileName.text = currentDocument.nameDocument

            setOnClickListener {
                onItemPosClick.onItemDocumentPosClick(position)
            }
        }
    }

    override fun getItemCount(): Int = mListDocument.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}