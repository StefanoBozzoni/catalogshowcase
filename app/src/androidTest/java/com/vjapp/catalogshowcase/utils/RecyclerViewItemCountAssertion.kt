package com.vjapp.catalogshowcase.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

import androidx.test.espresso.NoMatchingViewException

import androidx.test.espresso.ViewAssertion
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`


class RecyclerViewItemCountAssertion : ViewAssertion {
    private val matcher: Matcher<Int?>?

    constructor(expectedCount: Int) {
        matcher = `is`(expectedCount)
    }

    constructor(matcher: Matcher<Int?>?) {
        this.matcher = matcher
    }

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView?
        val adapter = recyclerView!!.adapter
        assertThat(adapter!!.itemCount, matcher)
    }

}
