package com.vjapp.catalogshowcase.utils

import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException

//example of usage
//Espresso.onView(isRoot()).perform(
//waitUntilShown(R.id.fab_color_1, 5000)
//waitUntilIdle(EspressoIdlingResource.countingIdlingResource,5000)
//)
fun waitUntilShown(viewId: Int, millis: Long): ViewAction {
    // --> see https://stackoverflow.com/questions/59689109/how-to-wait-for-async-task-in-espresso-without-idlingresource
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isRoot()
        }

        override fun getDescription(): String {
            return "wait for a specific view with id <$viewId> is shown during $millis millis."
        }

        override fun perform(uiController: UiController, view: View?) {
            uiController.loopMainThreadUntilIdle()
            val startTime = System.currentTimeMillis()
            val endTime = startTime + millis
            val viewMatcher: Matcher<View> = ViewMatchers.withId(viewId)
            do {
                for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                    // found view with required ID
                    if (viewMatcher.matches(child) && child.isShown) {
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
}


//example of usage:
//Espresso.onView(isRoot()).perform(
//  waitUntilShown(R.id.fab_color_1, 5000)
//)

fun waitUntilIdle(idlingResource: CountingIdlingResource, millis: Long): ViewAction {
    // --> see https://stackoverflow.com/questions/59689109/how-to-wait-for-async-task-in-espresso-without-idlingresource
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isRoot()
        }

        override fun getDescription(): String {
            return "waiting for a specific idlingResource to be idle for max $millis millis."
        }

        override fun perform(uiController: UiController, view: View?) {
            uiController.loopMainThreadUntilIdle()
            val startTime = System.currentTimeMillis()
            val endTime = startTime + millis
            do {
                if (idlingResource.isIdleNow) {
                    return
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
