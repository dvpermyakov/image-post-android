package com.dvpermyakov.imagepostapplication.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.toBitmap
import com.dvpermyakov.base.extensions.*
import com.dvpermyakov.base.fragments.BaseMvpFragment
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.adapters.CoverAdapter
import com.dvpermyakov.imagepostapplication.models.*
import com.dvpermyakov.imagepostapplication.presenters.CreateImagePostPresenter
import com.dvpermyakov.imagepostapplication.transformations.CircleTransformation
import com.dvpermyakov.imagepostapplication.utils.ImagePostApplicationConstants
import com.dvpermyakov.imagepostapplication.views.CreateImagePostView
import com.dvpermyakov.imagepostapplication.widgets.DraggableImageView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.squareup.picasso.Picasso
import io.michaelrocks.lightsaber.getInstance
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_image_post.*
import kotlinx.android.synthetic.main.layout_image_post_header.*
import kotlinx.android.synthetic.main.layout_post.*
import org.jetbrains.anko.toast
import java.io.File

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class CreateImagePostFragment : BaseMvpFragment<CreateImagePostView, CreateImagePostPresenter>(), CreateImagePostView {
    private val compositeDisposable = CompositeDisposable()
    private val adapter by lazy {
        CoverAdapter().apply {
            itemClickListener = { presenter.onCoverItemClick(it) }
            addClickListener = { presenter.onAddCoverClick() }
        }
    }
    private val trashCircleTransformation by lazy {
        CircleTransformation(baseActivity.getCompatColor(R.color.colorPrimary), baseActivity.getCompatColor(R.color.colorBackground))
    }

    override val baseView = this
    override val contentResId = R.layout.fragment_image_post

    override fun createPresenter(): CreateImagePostPresenter = getApplicationInjector().getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stickerButtonView.setOnClickListener {
            presenter.onStickerButtonClick()
        }
        fontButtonView.setOnClickListener {
            presenter.onFontClick()
        }
        imageView.setOnClickListener {
            presenter.onPostImageClick()
        }
        coverView.setOnClickListener {
            presenter.onPostImageClick()
        }
        saveButtonView.setOnClickListener {
            val hadEditTextFocus = editTextView.hasFocus()
            editTextView.clearFocus()
            presenter.onSaveClick(postView.toBitmap())
            if (hadEditTextFocus) {
                editTextView.requestFocus()
            }
        }

        view.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            presenter.onViewSizeChange()
        }

        compositeDisposable.add(RxTextView.textChanges(editTextView).subscribe { text ->
            saveButtonView.isEnabled = text.isNotEmpty()
        })

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }

    override fun onStop() {
        baseActivity.hideKeyboard()
        super.onStop()
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE_FROM_GALLERY -> data?.let {
                    presenter.onImagePick(it.data)
                }
                REQUEST_CODE_STICKERS -> data?.let {
                    presenter.onStickerAdd(it.extras.getParcelable(ImagePostApplicationConstants.INTENT_EXTRA_STICKER_MODEL))
                }
            }
        }
    }

    override fun setCoverList(items: List<SelectableCoverModel>) {
        adapter.items = items
    }

    override fun notifyCoverItemChanged(position: Int) {
        adapter.notifyItemChanged(position)
    }

    override fun notifyCoverItemAdded(position: Int) {
        adapter.notifyItemInserted(position)
    }

    override fun showStickerList() {
        editTextView.clearFocus()
        baseActivity.invokeOrHideKeyboardWithInvokable {
            baseActivity.addFragment(StickerListFragment.newInstance().apply {
                setTargetFragment(this@CreateImagePostFragment, REQUEST_CODE_STICKERS)
            })
        }
    }

    override fun addSticker(stickerUi: StickerUiModel) {
        postView.post {
            val stickerImageView = DraggableImageView(baseActivity).apply {
                scaleType = ImageView.ScaleType.MATRIX
                layoutParams = ViewGroup.LayoutParams(postView.width, postView.height)
                draggableModel = stickerUi
                positionChangeListener = { isInsideParent ->
                    Picasso.get()
                            .load(if (isInsideParent && !isIntersectedBy(trashView)) R.drawable.ic_fab_trash else R.drawable.ic_fab_trash_released)
                            .transform(trashCircleTransformation)
                            .into(trashView)
                }
                motionStateListener = { isInMotion, isInsideParent ->
                    val isIntersectedByTrashView = isIntersectedBy(trashView)
                    trashView.setVisible(isInMotion)
                    Picasso.get()
                            .load(if (isInsideParent && !isIntersectedByTrashView) R.drawable.ic_fab_trash else R.drawable.ic_fab_trash_released)
                            .transform(trashCircleTransformation)
                            .into(trashView)
                    if (!isInMotion && (!isInsideParent || isIntersectedByTrashView)) {
                        presenter.onStickerRemove(stickerUi)
                        onDispose()
                        setVisible(false)
                    }
                }
            }
            postView.addView(stickerImageView)
            postView.bringChildToFront(editTextView)

            Picasso.get()
                    .load(stickerUi.sticker.image)
                    .resize(stickerUi.width, stickerUi.height)
                    .centerInside()
                    .into(stickerImageView)
        }
    }

    override fun showKeyboard() {
        editTextView.requestFocus()
        baseActivity.showKeyboard()
    }

    override fun updateTextPostAppearance(cover: CoverModel, textAppearance: TextAppearanceModel) {
        with(editTextView) {
            setTextColor(baseActivity.getCompatColor(textAppearance.getTextColor(cover)))
            setHintTextColor(baseActivity.getCompatColor(textAppearance.getHintTextColor(cover)))
            setTextBackgroundColor(baseActivity.getCompatColor(textAppearance.getBackgroundColor()))
        }
    }

    override fun updateImagePostAppearance(cover: CoverModel) {
        when (cover) {
            is EmptyColorCoverModel -> {
                coverView.setVisible(true)
                val backgroundColor = baseActivity.getCompatColor(R.color.colorBackground)
                coverView.cover = ColorCoverModel(backgroundColor, backgroundColor)
                imageView.setVisible(false)
            }
            is ColorCoverModel -> {
                coverView.setVisible(true)
                coverView.cover = cover
                imageView.setVisible(false)
            }
            is ImageCoverModel -> {
                coverView.setVisible(false)
                imageView.setVisible(true)
                Picasso.get()
                        .load(cover.imageLarge)
                        .resize(POST_IMAGE_MAX_SIZE, POST_IMAGE_MAX_SIZE)
                        .centerInside()
                        .onlyScaleDown()
                        .into(imageView)
            }
            is FileCoverModel -> {
                coverView.setVisible(false)
                imageView.setVisible(true)
                Picasso.get()
                        .load(File(cover.path))
                        .resize(POST_IMAGE_MAX_SIZE, POST_IMAGE_MAX_SIZE)
                        .centerInside()
                        .onlyScaleDown()
                        .into(imageView)
            }
        }
    }

    override fun updateTrashAppearance(cover: CoverModel) {
        trashCircleTransformation.withStroke = cover is EmptyColorCoverModel || !(trashView inside postView)
    }

    override fun openImageFromGallery() {
        startActivityForImageFromGallery(REQUEST_CODE_IMAGE_FROM_GALLERY)
    }

    override fun showImageIsAddedError() {
        baseActivity.toast(R.string.app_open_image_from_gallery_error_exist_already)
    }

    override fun showReadPermissionDialog() {
        editTextView.clearFocus()
        baseActivity.invokeOrHideKeyboardWithInvokable {
            baseActivity.addFragment(PermissionFragment.newInstance(R.string.app_read_permissions_message, Manifest.permission.READ_EXTERNAL_STORAGE))
        }
    }

    override fun showWritePermissionDialog() {
        editTextView.clearFocus()
        baseActivity.invokeOrHideKeyboardWithInvokable {
            baseActivity.addFragment(PermissionFragment.newInstance(R.string.app_write_permissions_message, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        }
    }

    override fun showImageLoadingDialog() {
        baseActivity.showLoadingDialog(R.string.app_loading_dialog_image_message, TAG_LOADING_DIALOG_IMAGE)
    }

    override fun hideImageLoadingDialog() {
        baseActivity.hideLoadingDialog(TAG_LOADING_DIALOG_IMAGE)
    }

    override fun showImageLoadingError() {
        baseActivity.toast(R.string.app_image_loading_error)
    }

    override fun showSaveImageLoadingDialog() {
        baseActivity.showLoadingDialog(R.string.app_loading_dialog_image_saving_message, TAG_LOADING_DIALOG_SAVING_IMAGE)
    }

    override fun hideSaveImageLoadingDialog() {
        baseActivity.hideLoadingDialog(TAG_LOADING_DIALOG_SAVING_IMAGE)
    }

    override fun showSaveImageSuccess() {
        baseActivity.toast(R.string.app_image_post_save_success)
    }

    override fun showSaveImageFailure() {
        baseActivity.toast(R.string.app_image_post_save_failure)
    }

    companion object {
        private const val POST_IMAGE_MAX_SIZE = 2048
        private const val TAG_LOADING_DIALOG_IMAGE = "LoadingDialogImage"
        private const val TAG_LOADING_DIALOG_SAVING_IMAGE = "LoadingDiaSavinglogImage"

        private const val REQUEST_CODE_IMAGE_FROM_GALLERY = 4121
        private const val REQUEST_CODE_STICKERS = 9523

        fun newInstance() = CreateImagePostFragment()
    }
}