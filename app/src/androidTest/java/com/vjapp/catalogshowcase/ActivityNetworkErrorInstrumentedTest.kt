package com.vjapp.catalogshowcase

import android.os.SystemClock
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vjapp.catalogshowcase.adapters.CatalogAdapter
import com.vjapp.catalogshowcase.base.BaseKoinInstrumentedTest
import com.vjapp.catalogshowcase.di.configureEspressoTestAppComponent
import com.vjapp.catalogshowcase.domain.model.CatalogEntity
import com.vjapp.catalogshowcase.domain.model.CatalogItemEntity
import com.vjapp.catalogshowcase.utils.RecyclerViewHasTextAtPositionAssertion
import com.vjapp.catalogshowcase.utils.RecyclerViewItemCountAssertion
import com.vjapp.catalogshowcase.utils.waitUntilLoaded
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
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
 * This test check the application behaviour under network issues
 */

@RunWith(AndroidJUnit4::class)
class ActivityNetworkErrorInstrumentedTest: BaseKoinInstrumentedTest() {

    @Before
    fun start(){
        super.setUp()
        stopKoin() // to remove 'A Koin Application has already been started'
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            loadKoinModules(configureEspressoTestAppComponent(getMockWebServerUrl()).toMutableList())
        }

    }

    @Test
    fun CatalogSearchNetworkErrorTest() {
        mockAllNetworkResponsesWithJson(HttpURLConnection.HTTP_NOT_FOUND)

        launch(CatalogSearchActivity::class.java) //equivalent to launchactivity java
        SystemClock.sleep(3000) //wait for the mockwebserve to come up
        onView(withId(R.id.image_view_icon_error)).check(matches(isDisplayed()))
    }

    @Test
    fun DetailActivityNetworkErrorTest() {
        mockAllNetworkResponsesWithJson(HttpURLConnection.HTTP_NOT_FOUND)

        launch(DetailActivity::class.java) //equivalent to launchactivity java
        SystemClock.sleep(2000) //wait for the mockwebserve to come up
        onView(withId(R.id.image_view_icon_error)).check(matches(isDisplayed()))

    }


}
