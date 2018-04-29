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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_cover, parent, false))

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.itemView.coverView.coverModel = items[position]
    }
}