package com.dvpermyakov.imagepostapplication.utils

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.dvpermyakov.imagepostapplication.utils.matchers.HasBackgroundColorMatcher
import com.dvpermyakov.imagepostapplication.utils.view_actions.WaitTextAction

fun waitText(@StringRes text: Int, millis: Long) = WaitTextAction(text, millis)

fun hasBackgroundColor(@ColorRes colorId: Int) = HasBackgroundColorMatcher(colorId)