package com.vjapp.catalogshowcase


import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.vjapp.catalogshowcase.adapters.CatalogAdapter
import com.vjapp.catalogshowcase.base.BaseKoinInstrumentedTest
import com.vjapp.catalogshowcase.di.configureEspressoTestAppComponent
import com.vjapp.catalogshowcase.presentation.EspressoIdlingResource
import com.vjapp.catalogshowcase.utils.swipeUpCustom
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import java.net.HttpURLConnection


//TO RUN THIS TEST YOU HAVE TO DISABLE ALL ANIMATIONS ON EMULATOR OR IT WILL FAIL AS IT?S THE CORRECT BEHAVIOUR
//Note: Using idlingResource i successfully removed all the SystemClock.sleep(X) waiting times, so now the test is more reliable
@LargeTest
@RunWith(AndroidJUnit4::class)
class FunctionalityTest: BaseKoinInstrumentedTest() {

    /*
    @get:Rule  //this rule is unnecessary in this test but it is used to launch the activity before
    // each test and close it after each test. refer to : https://developer.android.com/guide/components/activities/testing
    var mActivityTestRule: ActivityScenarioRule<CatalogSearchActivity> = ActivityScenarioRule(CatalogSearchActivity::class.java)

     */

    /*
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(CatalogSearchActivity::class.java)

     */

    @Before
    override fun setUp() {
        super.setUp()
        stopKoin() // to remove 'A Koin Application has already been started'
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            loadKoinModules(configureEspressoTestAppComponent(getMockWebServerUrl()).toMutableList())
        }
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun recordedTest_1() {
        mockAllNetworkResponsesWithJson(HttpURLConnection.HTTP_OK)

        launch(CatalogSearchActivity::class.java).use {
            //SystemClock.sleep(2000)  not necessary anymore since we are using an Idling Resource!

            Espresso.onIdle() //wait until onIdle

            val recyclerView = onView(
                allOf(
                    withId(R.id.rv_catalog_list),
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        1
                    )
                )
            )
            Espresso.onIdle() //wait until onIdle
            recyclerView.perform(actionOnItemAtPosition<ViewHolder>(6, click()))
            Espresso.onIdle() //wait until onIdle

            val nestedScrollView = onView(withId(R.id.nested_scroll_view))
            nestedScrollView.perform(swipeUpCustom())

            val textView = onView(
                    allOf(withId(android.R.id.text1), withText("46"),
                            withParent(allOf(withId(R.id.spSizes),
                                    withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java)))),
                            isDisplayed()))
            textView.check(matches(withText("46")))

            val appCompatImageButton = onView(
                    allOf(childAtPosition(
                                    allOf(withId(R.id.action_bar),
                                            childAtPosition(
                                                    withId(R.id.action_bar_container),
                                                    0)),
                                    1),
                            isDisplayed()))
            //SystemClock.sleep(4000) //wait for the mockwebserve to come up
            appCompatImageButton.perform(click())
            //SystemClock.sleep(4000) //wait for the mockwebserve to come up

            //pressBack()
            val textView2 = onView(
                    allOf(withId(R.id.tvCategory), withText("Sneakers"),
                            withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                            isDisplayed()))
            textView2.check(matches(withText("Sneakers")))

            val recyclerView2 = onView(
                    allOf(withId(R.id.rv_catalog_list),
                            childAtPosition(
                                    withClassName(`is`("android.widget.FrameLayout")),
                                    1)))

            recyclerView2.perform(scrollToPosition<CatalogAdapter.ListViewHolder>(15))
            //SystemClock.sleep(1000) //wait for the mockwebserve to come up
            recyclerView2.perform(actionOnItemAtPosition<CatalogAdapter.ListViewHolder>(15, click()))
            //SystemClock.sleep(1000) //wait for the mockwebserve to come up

            val appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                    childAtPosition(
                        allOf(withId(R.id.action_bar),
                            childAtPosition(
                                withId(R.id.action_bar_container),
                                0)),
                        1),
                    isDisplayed()))
            appCompatImageButton2.perform(click())

            val recyclerView3 = onView(
                allOf(withId(R.id.rv_catalog_list),
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        1)))


            //Espresso.onIdle()

            //SystemClock.sleep(500) //this is still needed because we are scrollling pages in a paginating way
            //we have to put idling resource too here
            (1..8).forEach { i->
                recyclerView3.perform(scrollToPosition<ViewHolder>(i*10))
            }

            recyclerView3.check(matches(isDisplayed()))
            recyclerView3.perform(actionOnItemAtPosition<ViewHolder>(80, click()))

            val appCompatImageButton3 = onView(
                allOf(withContentDescription("Navigate up"),
                    childAtPosition(
                        allOf(withId(R.id.action_bar),
                            childAtPosition(
                                withId(R.id.action_bar_container),
                                0)),
                        1),
                    isDisplayed()))

            appCompatImageButton3.perform(click())

            val textView4 = onView(
                allOf(withId(R.id.tvCategory), withText("Giubbotto"),
                    withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                    isDisplayed()))

            val realText = textView4.check(matches(isDisplayed())).getExtractedText()
            Log.d("XDEBUG", "real text is $realText")

            val textView3 = onView(
                allOf(withId(R.id.tvCategory), withText("Giubbotto"),
                    withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                    isDisplayed()))
            textView3.check(matches(withText("Giubbotto")))

            //pressBack()
        }
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }



    fun ViewInteraction.getExtractedText(): String {
        var text: String? = null
        perform(
            object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return isDisplayed()
                }

                override fun getDescription(): String {
                    return "Get text from TextView"
                }

                override fun perform(uiController: UiController?, view: View?) {
                    if (view is TextView) {
                        text = view.text.toString()
                    }
                }
            }
        )

        if (text == null) {
            throw AssertionError("TextView not found or does not contain text.")
        }

        return text.toString()
    }

}
