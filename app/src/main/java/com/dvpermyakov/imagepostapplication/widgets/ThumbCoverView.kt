package com.dvpermyakov.imagepostapplication.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.annotation.DrawableRes
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withScale
import com.dvpermyakov.base.extensions.getCompatColor
import com.dvpermyakov.base.extensions.getInjector
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.interactors.BitmapInteractor
import com.dvpermyakov.imagepostapplication.models.*
import com.dvpermyakov.imagepostapplication.utils.PaintFactory
import io.michaelrocks.lightsaber.getInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by dmitrypermyakov on 29/04/2018.
 */

class ThumbCoverView : View {
    private val compositeDisposable = CompositeDisposable()
    private val radius = resources.getDimension(R.dimen.size_xsmall)
    private var rect: RectF? = null

    private val strokePaint = PaintFactory.createStrokePaint(context.getCompatColor(R.color.colorPrimary), STROKE_WIDTH)
    private var fillPaint: Paint? = null

    var selectableCover: SelectableCoverModel? = null
        set(value) {
            field = value
            invalidatePaint()
            invalidate()
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        invalidatePaint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rect?.let { rect ->
            selectableCover?.let { selectableCover ->
                fillPaint?.let { paint ->
                    if (selectableCover.selected) {
                        canvas.withScale(SELECTED_SCALE_STROKE, SELECTED_SCALE_STROKE, rect.centerX(), rect.centerY()) {
                            drawRoundRect(rect, radius, radius, strokePaint)
                            withScale(SELECTED_SCALE_FILL, SELECTED_SCALE_FILL, rect.centerX(), rect.centerY()) {
                                canvas.drawRect(rect, paint)
                            }
                        }
                    } else {
                        canvas.drawRoundRect(rect, radius, radius, paint)
                    }
                } ?: post { invalidatePaint() }
            }
        }
    }

    override fun onDetachedFromWindow() {
        invalidateDisposables()
        super.onDetachedFromWindow()
    }

    private fun invalidatePaint() {
        invalidateDisposables()
        rect?.let { rect ->
            selectableCover?.cover?.let { cover ->
                fillPaint = when (cover) {
                    is EmptyColorCoverModel -> PaintFactory.createGradientColorPaint(cover.colorStart, cover.colorEnd, rect)
                    is ColorCoverModel -> PaintFactory.createGradientColorPaint(cover.colorStart, cover.colorEnd, rect)
                    else -> PaintFactory.createEmptyPaint()
                }
                if (cover is ImageCoverModel) {
                    loadBitmapFromDrawable(cover.imageThumb)
                } else if (cover is FileCoverModel) {
                    loadBitmapFromPath(cover.path)
                }
            }
        }
    }

    private fun invalidateDisposables() {
        compositeDisposable.clear()
    }

    private fun loadBitmapFromDrawable(@DrawableRes drawable: Int) {
        getInjector()?.getInstance<BitmapInteractor>()?.let { interactor ->
            compositeDisposable.add(interactor.getBitmap(drawable)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnDispose {
                        fillPaint = null
                    }
                    .subscribe({ bitmap ->
                        fillPaint = PaintFactory.createBitmapPaint(bitmap)
                        invalidate()
                    }, {}))
        }
    }

    private fun loadBitmapFromPath(path: String) {
        getInjector()?.getInstance<BitmapInteractor>()?.let { interactor ->
            compositeDisposable.add(interactor.getBitmap(path, width)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnDispose {
                        fillPaint = null
                    }
                    .subscribe({ bitmap ->
                        fillPaint = PaintFactory.createBitmapPaint(bitmap)
                        invalidate()
                    }, {}))
        }
    }

    companion object {
        private const val SELECTED_SCALE_STROKE = .92f
        private const val SELECTED_SCALE_FILL = .75f

        private const val STROKE_WIDTH = 10f
    }
}