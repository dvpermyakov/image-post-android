package com.dvpermyakov.imagepostapplication

import com.dvpermyakov.imagepostapplication.interactors.StickerInteractor
import com.dvpermyakov.imagepostapplication.models.StickerModel
import com.dvpermyakov.imagepostapplication.repositories.StickerRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class StickerInteractorTest {

    private val stickerRepository = mockk<StickerRepository>()

    private val interactor = StickerInteractor(
            stickerRepository = stickerRepository
    )

    @Test
    fun testStickerList() {
        val stickers = listOf(
                StickerModel("path1"),
                StickerModel("path2"),
                StickerModel("path3")
        )
        every { stickerRepository.getStickers() } returns Single.just(stickers)

        val observer = interactor.getStickers().test()
        observer.assertValueCount(1).assertValueAt(0, stickers)
    }
}