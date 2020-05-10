package com.dvpermyakov.imagepostapplication.utils.view_actions

import android.view.View
import androidx.annotation.StringRes
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException

class WaitTextAction(
        @StringRes
        private val text: Int,
        private val millis: Long
) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return ViewMatchers.isRoot()
    }

    override fun getDescription(): String {
        return "wait for a specific view with text <$text> during $millis millis."
    }

    override fun perform(uiController: UiController, view: View?) {
        uiController.loopMainThreadUntilIdle()
        val startTime = System.currentTimeMillis()
        val endTime = startTime + millis
        val viewMatcher: Matcher<View> = ViewMatchers.withText(text)
        do {
            for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                if (viewMatcher.matches(child)) {
                    return
                }
            }
            uiController.loopMainThreadForAtLeast(50)
        } while (System.currentTimeMillis() < endTime)

        throw PerformException.Builder()
                .withActionDescription(this.description)
                .withViewDescription(HumanReadables.describe(view))
                .withCause(TimeoutException())
                .build()
    }
}