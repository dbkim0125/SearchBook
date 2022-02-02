package com.sendbird.assignment_android.searchbook

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sendbird.assignment_android.searchbook.activity.SearchActivity
import com.sendbird.assignment_android.searchbook.adapter.SearchResultAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchResultTest {

    private lateinit var keyword: String

    @get:Rule
    var activityRule: ActivityScenarioRule<SearchActivity>
            = ActivityScenarioRule(SearchActivity::class.java)

    @Before
    fun initKeyword() {
        keyword = "iot"
    }

    @Test
    fun testSearchBook() {
//        val scenario = activityRule.scenario

        onView(withId(R.id.search_edit))
            .perform(typeText("iot"), closeSoftKeyboard())
        onView(withId(R.id.search_button)).perform(click())

        onView(withId(R.id.search_result_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<SearchResultAdapter.SearchResultItemViewHolder>(0, click()))
    }
}