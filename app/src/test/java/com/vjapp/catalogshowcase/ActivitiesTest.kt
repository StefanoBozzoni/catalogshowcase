package com.vjapp.catalogshowcase

import android.content.pm.ActivityInfo
import android.os.Build.VERSION_CODES.O
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.Fragment
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.annotation.Config

/*
 Note : This test fail because Picasso handle some background thread , i tried to mock it but it doesn't work
 maybe i should also mock network requests using a Roboelectric Application
 */
@RunWith(AndroidJUnit4::class)
@Config(sdk = [O])
@ExperimentalCoroutinesApi
//@LooperMode(LooperMode.Mode.PAUSED)
class ActivitiesTest : AutoCloseKoinTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun start() {
        MockKAnnotations.init(this)
    }

    @Test
    fun activity_CatalogSearch() {

        //scenario.recreate()
        /*
        val picasso: Picasso = mockk(relaxed = true)
        Picasso.setSingletonInstance(picasso)
        val requestCreator: RequestCreator = mockk(relaxed = true)
        every { Picasso.get().load(nullable(String::class.java)) } returns requestCreator
        every { picasso.load(nullable(Uri::class.java)) } returns requestCreator
        every { picasso.load(nullable(File::class.java)) } returns requestCreator
        */

        // GIVEN
        val scenario = launchActivity<CatalogSearchActivity>()
        scenario.onActivity { activity -> assert(activity != null) }

    }

    @Test
    fun activity_Detail() {
        val scenario = launchActivity<DetailActivity>()

        scenario.recreate()

        scenario.onActivity { activity ->

            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

            val tvPrice = activity.findViewById<TextView>(R.id.tvPrice)

            val fab_color_1 = activity.findViewById<FloatingActionButton>(R.id.fab_color_1)

            fab_color_1.performClick()

            val fragmentList: List<Fragment> = activity.getSupportFragmentManager().getFragments()
            val mFragment: DetailFragment?
            if (fragmentList[0] is DetailFragment) {
                mFragment = fragmentList[0] as DetailFragment

                //shadowOf(getMainLooper()).idle()

                System.out.println(mFragment.javaClass.simpleName)
                System.out.println(mFragment.choosenColor)


                assert(mFragment.choosenColor == 0 && activity != null && tvPrice.text != "EUR 510,00")

            }
        }
    }

}