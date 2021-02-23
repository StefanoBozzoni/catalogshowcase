package com.vjapp.catalogshowcase.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.platform.app.InstrumentationRegistry

inline fun waitUntilLoaded(crossinline recyclerProvider: () -> RecyclerView) {
    Espresso.onIdle()

    lateinit var recycler: RecyclerView

    InstrumentationRegistry.getInstrumentation().runOnMainSync {
        recycler = recyclerProvider()
    }

    while (recycler.hasPendingAdapterUpdates()) {
        Thread.sleep(10)
    }
}