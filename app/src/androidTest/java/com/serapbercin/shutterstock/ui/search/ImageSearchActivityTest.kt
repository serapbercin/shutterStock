package com.serapbercin.shutterstock.ui.search

import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.serapbercin.shutterstock.R
import com.serapbercin.shutterstock.gsonUpperCamel
import com.serapbercin.shutterstock.parseFile
import com.serapbercin.shutterstock.runOnMainSync
import com.serapbercin.shutterstock.ui.search.data.ImageSearchFormData
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ImageSearchActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<ImageSearchActivity>(
            ImageSearchActivity::class.java, true)


    @Test
    fun showImageSearchList_whenUpdateImageSearchIsCalled() {
        val imageSearchResponse = createImageSearchResponse(
                "image_search_response_success.json")
        val activity = activityTestRule.activity
        runOnMainSync { activity.showData(imageSearchResponse.imageSearchListData, false) }
        Espresso.onView((ViewMatchers.withId(R.id.rv_image_search)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun showProgress_whenShowProgressIsCalled_thenShowProgressDialog() {
        runOnMainSync { activityTestRule.activity.showDialog() }
        Espresso.onView(ViewMatchers.withId(R.id.pb_load_image))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun hideProgress_whenHideProgressIsCalledAfterShowProgress_thenHideProgressDialog() {
        runOnMainSync { activityTestRule.activity.showDialog() }
        runOnMainSync { activityTestRule.activity.hideDialog() }
        Espresso.onView(ViewMatchers.withId(R.id.pb_load_image))
                .check(ViewAssertions.matches(ViewMatchers
                        .withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }


    private fun createImageSearchResponse(fileName: String):
            ImageSearchFormData = fileName parseFile gsonUpperCamel

}
