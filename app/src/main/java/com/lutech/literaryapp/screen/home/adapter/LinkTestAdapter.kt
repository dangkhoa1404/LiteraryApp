package com.lutech.literaryapp.screen.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lutech.literaryapp.R
import com.lutech.literaryapp.model.LinkTest
import kotlinx.android.synthetic.main.item_list_link.view.tvLinkName
import kotlin.collections.ArrayList

class LinkTestAdapter(
    val mContext: Context,
    private var mListLinkTest: ArrayList<LinkTest>,
    private val onItemPosClick: OnItemLinkTestClickListener
) : RecyclerView.Adapter<LinkTestAdapter.MyViewHolder>() {

    interface OnItemLinkTestClickListener {
        fun onItemDocumentPosClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_link, parent, false)
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentDocument = mListLinkTest[position]

        holder.itemView.apply {
            tvLinkName.text = currentDocument.nameLinkTest

            setOnClickListener {
                onItemPosClick.onItemDocumentPosClick(position)
            }
        }
    }

    override fun getItemCount(): Int = mListLinkTest.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}