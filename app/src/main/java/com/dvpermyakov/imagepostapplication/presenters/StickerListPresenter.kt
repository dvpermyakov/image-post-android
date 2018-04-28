package com.dvpermyakov.imagepostapplication.presenters

import com.dvpermyakov.base.presenters.BaseFragmentPresenter
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

class StickerListPresenter @Inject constructor(
        private val stickerInteractor: StickerInteractor) : BaseFragmentPresenter<StickerListView>() {
    private val compositeDisposable = CompositeDisposable()

    private var items = listOf<StickerModel>()
        set(value) {
            field = value
            view?.showStickers(value)
        }

    override fun onStart() {
        super.onStart()
        if (items.isEmpty()) {
            compositeDisposable.add(stickerInteractor.getStickers()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        view?.showLoading()
                    }
                    .subscribe({ stickers ->
                        items = stickers
                        view?.hideLoading()
                    }, {
                        view?.showError()
                    }))
        }
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun detachView() {
        compositeDisposable.dispose()
        super.detachView()
    }

    fun onEmptyClick() {
        view?.showPreviousScreen()
    }

    fun onStickerClick(sticker: StickerModel) {

    }
}