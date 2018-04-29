package com.dvpermyakov.imagepostapplication.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dvpermyakov.base.adapters.BaseRecyclerViewAdapter
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.models.SelectableCoverModel
import kotlinx.android.synthetic.main.layout_cover.view.*
import kotlinx.android.synthetic.main.layout_cover_footer.view.*

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class CoverAdapter : BaseRecyclerViewAdapter<SelectableCoverModel>() {

    var addClickListener: (() -> Unit)? = null
    var itemClickListener: ((SelectableCoverModel) -> Unit)? = null

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
        with(holder) {
            if (itemViewType == TYPE_ITEM) {
                with(itemView) {
                    frameLayoutView.setOnClickListener {
                        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                            itemClickListener?.invoke(items[holder.adapterPosition])
                        }

                    }
                    coverView.selectableCover = items[position]
                }
            } else {
                itemView.footerFrameLayoutView.setOnClickListener {
                    addClickListener?.invoke()
                }
            }
        }
    }

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_FOOTER = 1
        private const val FOOTER_SIZE = 1
    }
}