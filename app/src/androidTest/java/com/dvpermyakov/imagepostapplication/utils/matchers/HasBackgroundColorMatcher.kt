package com.dvpermyakov.imagepostapplication.utils.matchers

import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class HasBackgroundColorMatcher(@ColorRes private val colorId: Int) : TypeSafeMatcher<View>() {

    override fun describeTo(description: Description) {
        description.appendText("has background with color id = $colorId")
    }

    override fun matchesSafely(item: View): Boolean {
        val fromColorId = ContextCompat.getColor(item.context, colorId)
        val itemColor = (item.background as ColorDrawable).color
        return itemColor == fromColorId
    }

}