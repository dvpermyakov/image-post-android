package com.dvpermyakov.imagepostapplication

import android.Manifest
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.dvpermyakov.imagepostapplication.activities.MainActivity
import com.dvpermyakov.imagepostapplication.utils.matchers.ToastMatcher
import com.dvpermyakov.imagepostapplication.utils.waitText
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CreateAndSavePostTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val readPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Test
    fun setTextAndSave() {
        onView(withId(R.id.postView))
                .check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.trashView))
                .check(matches(not(isDisplayed())))

        onView(withId(R.id.editTextView))
                .perform(typeText("This is very huge text that I would like to type"))
                .check(matches(isCompletelyDisplayed()))

        onView(withId(R.id.fontButtonView))
                .perform(click())
                .perform(click())
                .check(matches(isCompletelyDisplayed()))

        onView(withId(R.id.editTextView))
                .check(matches(hasTextColor(R.color.textColorWhite)))

        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        onView(withId(R.id.stickerButtonView))
                .perform(click())

        onView(withId(R.id.titleView))
                .check(matches(isCompletelyDisplayed()))

        onView(withId(R.id.stickerRecyclerView))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.saveButtonView))
                .perform(click())

        onView(withText(R.string.app_loading_dialog_image_saving_message))
                .check(matches(isCompletelyDisplayed()))

        onView(isRoot()).inRoot(ToastMatcher())
                .perform(waitText(R.string.app_image_post_save_success, 10_000))
                .check(matches(isCompletelyDisplayed()))
    }
}