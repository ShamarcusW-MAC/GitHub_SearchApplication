package com.example.github_searchapplication.view

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.github_searchapplication.R
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Rule
    @JvmField
    var rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    var mainActivity: MainActivity? = null


    @Before
    @Throws(Exception::class)
    fun setUp() {

        mainActivity = rule.getActivity()
    }

    @Test
    fun testLaunch() {
        val view: View = mainActivity!!.findViewById(R.id.username_recyclerview)
        assertNotNull(view)

    }

    @Test
    fun ensureRecyclerView() {
        onView(withId(R.id.username_edittext)).perform(ViewActions.clearText())
            .perform(ViewActions.typeText("Shamarcus"), closeSoftKeyboard())
        onView(withId(R.id.username_recyclerview)).perform(click())
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {

        mainActivity = null
    }
}