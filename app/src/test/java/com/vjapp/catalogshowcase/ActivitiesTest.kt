package com.vjapp.catalogshowcase

import android.net.Uri
import android.os.Build.VERSION_CODES.Q
import android.view.View
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.mockito.ArgumentMatchers.nullable
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.util.concurrent.TimeoutException

/*
 Note : This test fail because Picasso handle some background thread , i tried to mock it but it doesn't work
 maybe i should also mock network requests using a Roboelectric Application
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Q])
@ExperimentalCoroutinesApi
//@LooperMode(LooperMode.Mode.PAUSED)
class ActivitiesTest : AutoCloseKoinTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

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
            picassoMocked = true
            val picasso: Picasso = mockk(relaxed = true)
            Picasso.setSingletonInstance(picasso)
            val requestCreator: RequestCreator = mockk(relaxed = true)
            every { Picasso.get().load(nullable(String::class.java)) } returns requestCreator
            every { picasso.load(nullable(Uri::class.java)) } returns requestCreator
            every { picasso.load(nullable(File::class.java)) } returns requestCreator
        }
    }

    @Before
    fun start() {
        MockKAnnotations.init(this)
        //note that IdlingResource aren't necessary since they work only for Espresso tests in AndroidTest
        //here we use waitUntilShown
        // --> see https://stackoverflow.com/questions/59689109/how-to-wait-for-async-task-in-espresso-without-idlingresource
    }

    @Test
    fun activity_Detail_Robo() {
        if (!picassoMocked) mockPicasso()
        val controller2 = Robolectric.buildActivity(DetailActivity::class.java)
        controller2.let { controller ->
            controller.setup() // Moves Activity to RESUMED state
            val activity: DetailActivity = controller.get()
            activity.findViewById<FloatingActionButton>(R.id.fab_color_1).performClick()
            val tvPrice = activity.findViewById<TextView>(R.id.tvPrice)
            val fragmentList: List<Fragment> = activity.getSupportFragmentManager().getFragments()
            val mFragment: DetailFragment?
            if (fragmentList[0] is DetailFragment) {

                Espresso.onView(isRoot()).perform(
                    waitUntilShown(R.id.fab_color_1, 5000)
                )
                Espresso.onView(withId(R.id.tvPrice)).check(matches((isDisplayed())))
                mFragment = fragmentList[0] as DetailFragment
                System.out.println(mFragment.javaClass.simpleName)
                System.out.println(mFragment.choosenColor)
                System.out.println(tvPrice.text)
                Espresso.onView(withId(R.id.tvPrice)).check(matches((isDisplayed())))
                Espresso.onView(withId(R.id.tvPrice)).check(matches(withText("EUR 510,00")))
            }
        }
    }

    @Test
    fun activity_Detail() {
        if (!picassoMocked) mockPicasso()
        val controller2 = Robolectric.buildActivity(DetailActivity::class.java)
        controller2.let { controller ->
            controller.setup() // Moves Activity to RESUMED state
            val activity: DetailActivity = controller.get()
            activity.findViewById<FloatingActionButton>(R.id.fab_color_1).performClick()
            val tvPrice = activity.findViewById<TextView>(R.id.tvPrice)
            val fragmentList: List<Fragment> = activity.getSupportFragmentManager().getFragments()
            val mFragment: DetailFragment?
            if (fragmentList[0] is DetailFragment) {
                Espresso.onView(isRoot()).perform(
                    waitUntilShown(R.id.fab_color_1, 5000)
                )
                mFragment = fragmentList[0] as DetailFragment
                System.out.println(mFragment.javaClass.simpleName)
                System.out.println(mFragment.choosenColor)
                System.out.println(tvPrice.text)
                assert(mFragment.choosenColor == 0 && activity != null && tvPrice.text == "EUR 510,00")
            }
        }
    }

    @Test
    fun activity_CatalogSearch() {
        if (!picassoMocked) mockPicasso()
        val scenario = launchActivity<CatalogSearchActivity>()
        scenario.onActivity { activity -> assert(activity != null) }

    }

    fun waitUntilShown(viewId: Int, millis: Long): ViewAction {
        // --> see https://stackoverflow.com/questions/59689109/how-to-wait-for-async-task-in-espresso-without-idlingresource
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for a specific view with id <$viewId> is shown during $millis millis."
            }

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadUntilIdle()
                val startTime = System.currentTimeMillis()
                val endTime = startTime + millis
                val viewMatcher: Matcher<View> = withId(viewId)
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

    @Test
    fun fragment_alternative_test() {
        if (!picassoMocked) mockPicasso()
        //alternative method to launch a fragment in an empty activity
        val scenarioFrag = launchFragmentInContainer<DetailFragment>(bundleOf(), R.style.AppTheme)
        scenarioFrag.onFragment { fragment ->
            Espresso.onView(isRoot()).perform(
                waitUntilShown(R.id.fab_color_1, 5000)
            )
            val tvPrice = fragment.activity?.findViewById<TextView>(R.id.tvPrice)
            System.out.println(fragment.javaClass.simpleName)
            System.out.println(fragment.choosenColor)
            System.out.println(tvPrice?.text)
            assert(fragment.choosenColor == -1 && fragment.activity != null && tvPrice?.text == "EUR 510,00")
        }
        scenarioFrag.close()
    }


}