package com.dvpermyakov.imagepostapplication.repositories

import android.content.res.AssetManager
import com.dvpermyakov.base.extensions.safetyRead
import com.dvpermyakov.imagepostapplication.models.StickerModel
import io.reactivex.Single
import io.reactivex.SingleEmitter
import org.json.JSONArray
import org.json.JSONException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

@Singleton
class StickerRepository @Inject constructor(private val assets: AssetManager) {
    private var cachedItems = listOf<StickerModel>()

    fun getStickers() = if (cachedItems.isNotEmpty()) {
        Single.just(cachedItems)
    } else {
        Single.create<List<StickerModel>> { emitter ->
            readItemsFromAssets(emitter)
        }
    }

    private fun readItemsFromAssets(emitter: SingleEmitter<List<StickerModel>>) {
        assets.safetyRead(STICKERS_JSON_FILE_NAME)?.let { json ->
            if (json.isNotEmpty()) {
                try {
                    val items = mutableListOf<String>()
                    val jsonArray = JSONArray(json)
                    for (index in 0 until jsonArray.length()) {
                        items.add(jsonArray.getString(index))
                    }
                    cachedItems = items.map { StickerModel(String.format(ASSET_URI, it)) }
                    emitter.onSuccess(cachedItems)
                } catch (ex: JSONException) {
                    emitter.onError(ex)
                }
            }
        }
    }

    companion object {
        private const val ASSET_URI = "file:///android_asset/%s"
        private const val STICKERS_JSON_FILE_NAME = "stickers.json"
    }

}