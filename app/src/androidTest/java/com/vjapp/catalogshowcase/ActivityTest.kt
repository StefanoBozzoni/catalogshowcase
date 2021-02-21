package com.vjapp.catalogshowcase

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
//@Config(sdk = [Build.VERSION_CODES.O])
class ActivityTest {

    @Test
    fun sumTest() {
        val scenario =launch(CatalogSearchActivity::class.java) //equivalent to launchactivity java
        scenario.onActivity { activity->
            assertTrue(activity!=null)
        }
        //onView(withId(R.id.button_first)).perform(click())
    }


}