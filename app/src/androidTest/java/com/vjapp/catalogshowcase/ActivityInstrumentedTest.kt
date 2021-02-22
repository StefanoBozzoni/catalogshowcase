package com.vjapp.catalogshowcase

import android.os.SystemClock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragment
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vjapp.catalogshowcase.adapters.CatalogAdapter
import com.vjapp.catalogshowcase.base.BaseKoinInstrumentedTest
import com.vjapp.catalogshowcase.di.configureEspressoTestAppComponent
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import java.net.HttpURLConnection

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
//@Config(sdk = [Build.VERSION_CODES.O])
class ActivityInstrumentedTest: BaseKoinInstrumentedTest() {

    /*
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
   */

    @Before
    fun start(){
        //mockAllNetworkResponsesWithJson(HttpURLConnection.HTTP_OK)
        super.setUp()
        loadKoinModules(configureEspressoTestAppComponent(getMockWebServerUrl()).toMutableList())
    }

    @Test
    fun ActivityInstrumentedTest() {
        //mockNetworkResponseWithFileContent("catalog_response.json", HttpURLConnection.HTTP_OK)
        mockAllNetworkResponsesWithJson(HttpURLConnection.HTTP_OK)

        val scenario =launch(CatalogSearchActivity::class.java) //equivalent to launchactivity java
        SystemClock.sleep(3000)

        scenario.onActivity { activity->
            //onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
            //val scenario = launchFragment(null,CatalogSearchFragment::class.java)
            //onView(withId(R.id.rv_catalog_list)).perform(RecyclerViewActions.actionOnItemAtPosition<CatalogAdapter.ListViewHolder>(1, click()))
            //SystemClock.sleep(2000)
            //assertTrue(activity!=null)
            //onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        }
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_catalog_list)).perform(RecyclerViewActions.actionOnItemAtPosition<CatalogAdapter.ListViewHolder>(1, click()))

        //onView(withId(R.id.button_first)).perform(click())
    }


}

