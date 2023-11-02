package com.vjapp.catalogshowcase.utils

import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matcher

inline fun waitUntilLoaded(crossinline recyclerProvider: () -> RecyclerView) {
    Espresso.onIdle()

    lateinit var recycler: RecyclerView

    InstrumentationRegistry.getInstrumentation().runOnMainSync {
        recycler = recyclerProvider()
    }

    while (recycler.hasPendingAdapterUpdates()) {
        Thread.sleep(10)
    }
}

fun swipeUpCustom(): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(NestedScrollView::class.java)
        }

        override fun getDescription(): String {
            return "Swipe up on NestedScrollView"
        }

        override fun perform(uiController: UiController, view: View) {
            val scrollView = view as NestedScrollView
            //scrollView.scrollBy(0, 200) // Adjust the scroll amount as needed
            scrollView.scrollTo(0, scrollView.getChildAt(0).height)
        }
    }
}