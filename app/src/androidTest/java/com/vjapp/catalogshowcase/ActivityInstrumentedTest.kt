package com.vjapp.catalogshowcase

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vjapp.catalogshowcase.adapters.CatalogAdapter
import com.vjapp.catalogshowcase.base.BaseKoinInstrumentedTest
import com.vjapp.catalogshowcase.di.configureEspressoTestAppComponent
import com.vjapp.catalogshowcase.presentation.EspressoIdlingResource
import com.vjapp.catalogshowcase.utils.RecyclerViewHasTextAtPositionAssertion
import com.vjapp.catalogshowcase.utils.RecyclerViewItemCountAssertion
import com.vjapp.catalogshowcase.utils.swipeUpCustom
import com.vjapp.catalogshowcase.utils.waitUntilLoaded
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import java.net.HttpURLConnection


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

//NOTE: To perform this tests , the animations on the device should be disabled because the test will be cancelled if they are enabled
//To remove transition or animation on an Android emulator, you can follow these steps:
//Open the Android emulator.
//Select Settings.
//In the Settings window, click on Developer options.
//Scroll down to the Drawing section.
//Set the Window animation scale, Transition animation scale, and Animator duration scale values to 0.5x or Off.
//This will disable all animations on the Android emulator.
//
//You can also disable animations on the Android emulator using the following command:
//
//adb shell settings put global window_animation_scale 0
//adb shell settings put global transition_animation_scale 0
//adb shell settings put global animator_duration_scale 0

//to Re_enable :
//adb shell settings put global window_animation_scale 1.0
//adb shell settings put global transition_animation_scale 1.0
//adb shell settings put global animator_duration_scale 1.0

@RunWith(AndroidJUnit4::class)
class ActivityInstrumentedTest: BaseKoinInstrumentedTest() {

    @Before
    fun start(){
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
    fun ActivityCatalogSearchAndDetailTest() {
        mockAllNetworkResponsesWithJson(HttpURLConnection.HTTP_OK)

        val scenario =launch(CatalogSearchActivity::class.java) //equivalent to launchactivity java

        //val scenario2 = launchFragmentInContainer<DetailFragment>(bundleOf())

        //SystemClock.sleep(1000) //wait for the mockwebserve to come up

        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))  //La bottom bar è visibile

        var rvCatalog :RecyclerView? = null
        scenario.onActivity { activity->
            rvCatalog =  activity.findViewById(R.id.rv_catalog_list)
        }
        waitUntilLoaded { rvCatalog!! }

        onView(withId(R.id.rv_catalog_list)).check(RecyclerViewItemCountAssertion(40))
        System.out.println("click on a button")

        onView(withId(R.id.navigation_highest)).perform(click())
        waitUntilLoaded { rvCatalog!! }
        //SystemClock.sleep(2000) //wait , we can't use waitUntilLoaded here because it works only at first loaded layout
        //we could use an Idle Resource here ?

        onView(withId(R.id.rv_catalog_list)).check(RecyclerViewHasTextAtPositionAssertion(2,"Tappeto"))
        onView(withId(R.id.rv_catalog_list))
            .perform(RecyclerViewActions
            .actionOnItemAtPosition<CatalogAdapter.ListViewHolder>(2, click()))

        onView(withId(R.id.ivProductItem)).check((matches((isDisplayed()))))

    }

    @Test
    fun DetailActivityTest() {
        mockAllNetworkResponsesWithJson(HttpURLConnection.HTTP_OK)

        val scenario =launch(DetailActivity::class.java) //equivalent to launchactivity java
        //SystemClock.sleep(3000) //wait for the mockwebserve to come up

        onView(withId(R.id.tvPrice)).check((matches((isDisplayed()))))
        onView(withId(R.id.tvPrice)).check(matches(withText("EUR 510,00")))

        val nestedScrollView = onView(withId(R.id.nested_scroll_view))

        nestedScrollView.perform(swipeUpCustom())  //<<- scrolls to bottom , it's a custom scroll because the legacy one doesn't work!
        onView(withId(R.id.spSizes)).perform(click())
        onData(anything()).atPosition(1).perform(click())
        onView(withId(R.id.spSizes)).check(matches(withSpinnerText(containsString("48"))))

        onView(withId(R.id.fab_color_1)).perform(click())

        scenario.onActivity { activity->
            val fragmentList: List<Fragment> = activity.getSupportFragmentManager().getFragments()
            val mFragment: DetailFragment?
            if (fragmentList[0] is DetailFragment) {
                mFragment = fragmentList[0] as DetailFragment

                assert(mFragment.choosenColor==0 && activity != null)
            }
        }
    }


}

