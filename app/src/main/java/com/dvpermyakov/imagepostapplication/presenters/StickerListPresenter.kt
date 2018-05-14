package com.dvpermyakov.imagepostapplication.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.dvpermyakov.imagepostapplication.interactors.StickerInteractor
import com.dvpermyakov.imagepostapplication.models.StickerModel
import com.dvpermyakov.imagepostapplication.views.StickerListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

@InjectViewState
class StickerListPresenter @Inject constructor(private val stickerInteractor: StickerInteractor) : MvpPresenter<StickerListView>() {
    private val compositeDisposable = CompositeDisposable()
    private var items = listOf<StickerModel>()
        set(value) {
            field = value
            viewState.showStickers(value)
        }

    override fun attachView(view: StickerListView?) {
        super.attachView(view)
        if (items.isEmpty()) {
            compositeDisposable.add(stickerInteractor.getStickers()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        viewState.showLoading()
                    }
                    .subscribe({ stickers ->
                        items = stickers
                        viewState.hideLoading()
                    }, {
                        viewState.showError()
                    }))
        }
    }

    override fun detachView(view: StickerListView?) {
        compositeDisposable.dispose()
        super.detachView(view)
    }

    fun onEmptyClick() {
        viewState.showPreviousScreen()
    }

    fun onStickerClick(sticker: StickerModel) {
        viewState.sendChosenSticker(sticker)
        viewState.showPreviousScreen()
    }
}