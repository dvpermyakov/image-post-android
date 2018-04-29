package com.dvpermyakov.imagepostapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dvpermyakov.base.adapters.BaseRecyclerViewAdapter
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.models.CoverModel
import kotlinx.android.synthetic.main.layout_cover.view.*

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class CoverAdapter : BaseRecyclerViewAdapter<CoverModel>() {

    override fun getItemCount() = super.getItemCount() + FOOTER_SIZE

    override fun getItemViewType(position: Int) = if (position in items.indices) {
        TYPE_ITEM
    } else {
        TYPE_FOOTER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = if (viewType == TYPE_ITEM) {
        BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_cover, parent, false))
    } else {
        BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_cover_footer, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM) {
            with(holder.itemView) {
                frameLayoutView.setOnClickListener {  }
                coverView.coverModel = items[position]
            }
        } else {

        }
    }

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_FOOTER = 1
        private const val FOOTER_SIZE = 1
    }
}