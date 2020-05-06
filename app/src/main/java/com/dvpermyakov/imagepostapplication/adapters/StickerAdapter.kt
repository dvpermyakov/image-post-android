package com.dvpermyakov.imagepostapplication.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dvpermyakov.base.adapters.BaseRecyclerViewAdapter
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.models.StickerModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_sticker.view.*

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class StickerAdapter : BaseRecyclerViewAdapter<StickerModel>() {
    var clickListener: ((item: StickerModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_sticker, parent, false))

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = items[position]
        with(holder.itemView) {
            setOnClickListener {
                if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                    clickListener?.invoke(items[holder.adapterPosition])
                }
            }
            Picasso.get()
                    .load(item.image)
                    .resize(STICKER_SIZE, STICKER_SIZE)
                    .centerInside()
                    .into(imageView)
        }
    }

    companion object {
        private const val STICKER_SIZE = 256
    }
}