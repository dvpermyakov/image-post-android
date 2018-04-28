package com.dvpermyakov.imagepostapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dvpermyakov.base.adapters.BaseRecyclerViewAdapter
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.models.ColorCoverModel
import com.dvpermyakov.imagepostapplication.models.CoverModel

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class CoverAdapter : BaseRecyclerViewAdapter<CoverModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_cover, parent, false))

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = items[position]
        when (item) {
            is ColorCoverModel -> bindColorCover(holder.itemView, item)
        }
    }

    private fun bindColorCover(view: View, item: ColorCoverModel) {
        view.setBackgroundColor(item.color)
    }
}