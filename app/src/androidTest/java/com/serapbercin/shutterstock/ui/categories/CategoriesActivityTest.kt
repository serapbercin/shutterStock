package com.serapbercin.shutterstock.ui.categories

import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.serapbercin.shutterstock.R
import com.serapbercin.shutterstock.runOnMainSync
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CategoriesActivityTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<CategoriesActivity>(CategoriesActivity::class.java, true)

    @Test
    fun test() {

    }

}