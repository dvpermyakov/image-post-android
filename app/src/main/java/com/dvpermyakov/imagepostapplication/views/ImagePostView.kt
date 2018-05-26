package com.dvpermyakov.imagepostapplication.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.dvpermyakov.imagepostapplication.models.CoverModel
import com.dvpermyakov.imagepostapplication.models.SelectableCoverModel
import com.dvpermyakov.imagepostapplication.models.StickerUiModel
import com.dvpermyakov.imagepostapplication.models.TextAppearanceModel

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

@StateStrategyType(AddToEndStrategy::class)
interface ImagePostView : MvpView {
    @StateStrategyType(AddToEndStrategy::class)
    fun setCoverList(items: List<SelectableCoverModel>)

    @StateStrategyType(AddToEndStrategy::class)
    fun scrollToCoverItem(index: Int)

    @StateStrategyType(AddToEndStrategy::class)
    fun notifyCoverItemChanged(position: Int)

    @StateStrategyType(AddToEndStrategy::class)
    fun notifyCoverItemAdded(position: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showStickerList()

    @StateStrategyType(AddToEndStrategy::class)
    fun addSticker(stickerUi: StickerUiModel)

    @StateStrategyType(SkipStrategy::class)
    fun showClosedTrash(stickerUi: StickerUiModel)

    @StateStrategyType(SkipStrategy::class)
    fun showOpenedTrash(stickerUi: StickerUiModel)

    @StateStrategyType(SkipStrategy::class)
    fun hideTrash(stickerUi: StickerUiModel)

    @StateStrategyType(SkipStrategy::class)
    fun showKeyboard()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateTextPostAppearance(cover: CoverModel, textAppearance: TextAppearanceModel)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateImagePostAppearance(cover: CoverModel)

    @StateStrategyType(SkipStrategy::class)
    fun updateTrashAppearance(cover: CoverModel)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openImageFromGallery()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showImageIsAddedError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showReadPermissionDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showWritePermissionDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showImageLoadingDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideImageLoadingDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showImageLoadingError()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSaveImageLoadingDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideSaveImageLoadingDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSaveImageSuccess()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSaveImageFailure()
}