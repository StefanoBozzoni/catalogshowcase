package com.vjapp.catalogshowcase

import android.net.Uri
import android.os.Build.VERSION_CODES.Q
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.vjapp.catalogshowcase.presentation.EspressoIdlingResource
import com.vjapp.catalogshowcase.utils.swipeUpCustom
import com.vjapp.catalogshowcase.utils.waitUntilIdle
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.mockito.ArgumentMatchers.nullable
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

/*
 Note : This test fail because Picasso handle some background thread , i tried to mock it but it doesn't work
 maybe i should also mock network requests using a Roboelectric Application
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Q])
@ExperimentalCoroutinesApi
//@LooperMode(LooperMode.Mode.PAUSED)
class ActivitiesTest : AutoCloseKoinTest() {

    companion object {
        var picassoMocked = false
        @JvmStatic
        @BeforeClass
        fun initialize() {
            picassoMocked = false
        }
    }

    fun mockPicasso() {
        if (!picassoMocked) {
            val picasso: Picasso = mockk(relaxed = true)
            Picasso.setSingletonInstance(picasso)
            val requestCreator: RequestCreator = mockk(relaxed = true)
            every { Picasso.get().load(nullable(String::class.java)) } returns requestCreator
            every { picasso.load(nullable(Uri::class.java)) } returns requestCreator
            every { picasso.load(nullable(File::class.java)) } returns requestCreator
            picassoMocked = true
        }
    }

    @Before
    fun start() {
        MockKAnnotations.init(this)
        //note that IdlingResource aren't necessary since they work only for Espresso tests in AndroidTest
        //here we use waitUntilShown
        // --> see https://stackoverflow.com/questions/59689109/how-to-wait-for-async-task-in-espresso-without-idlingresource
        mockPicasso()
    }

    @Test
    fun activity_Detail_Robo() {
        val controller2 = Robolectric.buildActivity(DetailActivity::class.java)
        controller2.let { controller ->
            controller.setup() // Moves Activity to RESUMED state
            val activity: DetailActivity = controller.get()
            activity.findViewById<FloatingActionButton>(R.id.fab_color_1).performClick()
            val tvPrice = activity.findViewById<TextView>(R.id.tvPrice)
            val fragmentList: List<Fragment> = activity.getSupportFragmentManager().getFragments()
            val mFragment: DetailFragment?
            if (fragmentList[0] is DetailFragment) {
                Espresso.onView(isRoot()).perform(waitUntilIdle(EspressoIdlingResource.countingIdlingResource,5000))
                Espresso.onView(withId(R.id.tvPrice)).check(matches((isDisplayed())))
                mFragment = fragmentList[0] as DetailFragment
                System.out.println(mFragment.javaClass.simpleName)
                System.out.println(mFragment.choosenColor)
                System.out.println(tvPrice.text)
                Espresso.onView(withId(R.id.tvPrice)).check(matches((isDisplayed())))
                Espresso.onView(withId(R.id.tvPrice)).check(matches(withText("EUR 510,00")))

                val nestedScrollView = Espresso.onView(withId(R.id.nested_scroll_view))
                nestedScrollView.perform(swipeUpCustom())
                Espresso.onView(withId(R.id.spSizes)).perform(ViewActions.click())
                Espresso.onData(Matchers.anything()).atPosition(1).perform(ViewActions.click())
                Espresso.onView(withId(R.id.spSizes)).check(matches(ViewMatchers.withSpinnerText(Matchers.containsString("48"))))
            }
            activity.finish()
            controller.destroy()
        }
    }

    @Test
    fun activity_Detail() {
        val controller2 = Robolectric.buildActivity(DetailActivity::class.java)
        controller2.let { controller ->
            controller.setup() // Moves Activity to RESUMED state
            val activity: DetailActivity = controller.get()
            activity.findViewById<FloatingActionButton>(R.id.fab_color_1).performClick()
            val tvPrice = activity.findViewById<TextView>(R.id.tvPrice)
            val fragmentList: List<Fragment> = activity.getSupportFragmentManager().getFragments()
            if (fragmentList[0] is DetailFragment) {
                Espresso.onView(isRoot()).perform(waitUntilIdle(EspressoIdlingResource.countingIdlingResource, 5000))
                val mFragment = fragmentList[0] as DetailFragment
                System.out.println(mFragment.javaClass.simpleName)
                System.out.println(mFragment.choosenColor)
                System.out.println(tvPrice.text)
                assert(mFragment.choosenColor == 0 && tvPrice.text == "EUR 510,00")
            }
            activity.finish()
            controller.destroy()
        }
    }

    @Test
    fun activity_CatalogSearch() {
        val scenario = launchActivity<CatalogSearchActivity>()
        scenario.onActivity { activity ->
            assert(activity != null)
            activity.finish()
        }
        scenario.close()

    }

    @Test
    fun fragment_alternative_test() {
        //alternative method to launch a fragment in an empty activity
        val scenarioFrag = launchFragmentInContainer<DetailFragment>(bundleOf(), R.style.AppTheme)
        scenarioFrag.onFragment { fragment ->
            Espresso.onView(isRoot()).perform(waitUntilIdle(EspressoIdlingResource.countingIdlingResource, 5000))
            val tvPrice = fragment.activity?.findViewById<TextView>(R.id.tvPrice)
            System.out.println(fragment.javaClass.simpleName)
            System.out.println(fragment.choosenColor)
            System.out.println(tvPrice?.text)
            assert(fragment.choosenColor == -1 && fragment.activity != null && tvPrice?.text == "EUR 510,00")
        }
        scenarioFrag.close()
    }
}