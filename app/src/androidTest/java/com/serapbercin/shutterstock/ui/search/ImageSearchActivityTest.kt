package com.serapbercin.shutterstock.ui.search

import android.os.SystemClock
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.serapbercin.shutterstock.R
import com.serapbercin.shutterstock.gsonUpperCamel
import com.serapbercin.shutterstock.matchers.RecyclerViewMatcher
import com.serapbercin.shutterstock.matchers.withIdHasParentId
import com.serapbercin.shutterstock.parseFile
import com.serapbercin.shutterstock.runOnMainSync
import com.serapbercin.shutterstock.ui.search.data.ImageSearchFormData
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


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

    @Test
    fun showImageSearchList_whenNextPageImageSearchListItem() {
        val imageSearchResponse = createImageSearchResponse(
                "image_search_response_success.json")
        val activity = activityTestRule.activity
        runOnMainSync { activity.showData(imageSearchResponse.imageSearchListData, false) }
        Espresso.onView(withIdHasParentId(R.id.rv_image_search, R.id.rl_root_view))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(10))
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.tv_image_title),
                ViewMatchers.withParent(withRecyclerView(R.id.rv_image_search).atPosition(10))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }


    private fun createImageSearchResponse(fileName: String):
            ImageSearchFormData = fileName parseFile gsonUpperCamel

}
