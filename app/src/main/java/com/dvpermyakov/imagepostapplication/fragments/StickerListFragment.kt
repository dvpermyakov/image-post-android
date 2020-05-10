package com.dvpermyakov.imagepostapplication.fragments

import android.animation.Animator
import android.animation.AnimatorSet
import android.app.Activity
import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.dvpermyakov.base.extensions.setVisible
import com.dvpermyakov.base.fragments.BaseMoxyFragment
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.adapters.StickerAdapter
import com.dvpermyakov.imagepostapplication.models.StickerModel
import com.dvpermyakov.imagepostapplication.presenters.StickerListPresenter
import com.dvpermyakov.imagepostapplication.utils.AnimatorFactory
import com.dvpermyakov.imagepostapplication.utils.ImagePostApplicationConstants
import com.dvpermyakov.imagepostapplication.views.StickerListView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import io.michaelrocks.lightsaber.getInstance
import kotlinx.android.synthetic.main.fragment_sticker_list.*
import kotlinx.android.synthetic.main.layout_sticker_list.*
import kotlin.math.max
import kotlin.math.min

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class StickerListFragment : BaseMoxyFragment(), StickerListView {
    private var animation: AnimatorSet? = null
    private val adapter by lazy {
        StickerAdapter().apply {
            clickListener = { presenter.onStickerClick(it) }
        }
    }

    @InjectPresenter
    lateinit var presenter: StickerListPresenter

    @ProvidePresenter
    fun providePresenter(): StickerListPresenter {
        return appInjector.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_sticker_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyContainer.setOnClickListener {
            presenter.onEmptyClick()
        }

        // to prevent clicking on empty container
        titleView.setOnClickListener {
        }

        val layoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.SPACE_AROUND
        }

        stickerRecyclerView.layoutManager = layoutManager
        stickerRecyclerView.adapter = adapter
        stickerRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                setDividerViewAlpha(dy)
            }
        })

        savedInstanceState?.let {
            dividerView.alpha = it.getFloat(KEY_DIVIDER_VIEW_ALPHA)
        }

        mainContainerView.post {
            if (savedInstanceState == null) {
                animation = createAnimator()
                animation?.start()
            } else {
                mainContainerView.setVisible(true)
            }
        }
    }

    override fun onStop() {
        animation?.cancel()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat(KEY_DIVIDER_VIEW_ALPHA, dividerView.alpha)
    }

    override fun showLoading() {
        progressBarView.setVisible(true)
        stickerRecyclerView.setVisible(false)
        errorTextView.setVisible(false)
    }

    override fun hideLoading() {
        progressBarView.setVisible(false)
        stickerRecyclerView.setVisible(true)
        errorTextView.setVisible(false)
    }

    override fun showError() {
        progressBarView.setVisible(false)
        stickerRecyclerView.setVisible(false)
        errorTextView.setVisible(true)
    }

    override fun showStickers(list: List<StickerModel>) {
        adapter.items = list
    }

    override fun sendChosenSticker(sticker: StickerModel) {
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, Intent().apply {
            putExtra(ImagePostApplicationConstants.INTENT_EXTRA_STICKER_MODEL, sticker)
        })
    }

    override fun showPreviousScreen() {
        baseActivity.onBackPressed()
    }

    private fun setDividerViewAlpha(offset: Int) {
        dividerView?.alpha = max(0f, min(1f, dividerView.alpha + offset / DIVIDER_ALPHA_HEIGHT))
    }

    private fun createAnimator(): AnimatorSet {
        return AnimatorSet().apply {
            play(AnimatorFactory.createMovementAnimator(mainContainerView, PointF(0f, mainContainerView.height.toFloat()), PointF(0f, 0f), ANIMATION_DURATION_MS))
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator) = Unit

                override fun onAnimationStart(animation: Animator) {
                    mainContainerView.setVisible(true)
                    mainContainerView.translationX = 0f
                    mainContainerView.translationY = mainContainerView.height.toFloat()
                }

                override fun onAnimationEnd(animation: Animator) {
                    mainContainerView.translationX = 0f
                    mainContainerView.translationY = 0f
                }

                override fun onAnimationCancel(animation: Animator) {
                    mainContainerView.translationX = 0f
                    mainContainerView.translationY = 0f
                }
            })
        }
    }

    companion object {
        private const val KEY_DIVIDER_VIEW_ALPHA = "dividerAlpha"
        private const val DIVIDER_ALPHA_HEIGHT = 300f

        private const val ANIMATION_DURATION_MS = 400L

        fun newInstance() = StickerListFragment()
    }
}