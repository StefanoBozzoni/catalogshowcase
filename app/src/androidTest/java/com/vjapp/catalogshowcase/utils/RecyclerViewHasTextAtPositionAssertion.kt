package com.vjapp.catalogshowcase.utils

import android.view.View
import android.view.View.FIND_VIEWS_WITH_TEXT
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`


class RecyclerViewHasTextAtPositionAssertion(val position: Int, val expectedText: String) : ViewAssertion {

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {

        if (view !is RecyclerView) {
            throw NoSuchElementException("RecyclerView not found")
        }

        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val outviews: ArrayList<View> = ArrayList()
        view.findViewHolderForAdapterPosition(position)!!.itemView.findViewsWithText(
            outviews, expectedText,
            View.FIND_VIEWS_WITH_TEXT
        )

        val recyclerView = view as RecyclerView?
        val adapter = recyclerView!!.adapter
        MatcherAssert.assertThat<Boolean>(outviews.isNotEmpty(), `is`(true))
    }

}
