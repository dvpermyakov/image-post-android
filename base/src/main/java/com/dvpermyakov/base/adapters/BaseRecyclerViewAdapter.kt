package com.dvpermyakov.base.adapters

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder>() {
    var items = listOf<T>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view)
}