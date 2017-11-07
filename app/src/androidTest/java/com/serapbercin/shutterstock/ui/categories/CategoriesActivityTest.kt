package com.serapbercin.shutterstock.ui.categories

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.serapbercin.shutterstock.R
import com.serapbercin.shutterstock.gsonUpperCamel
import com.serapbercin.shutterstock.matchers.withIdHasParentId
import com.serapbercin.shutterstock.parseFile
import com.serapbercin.shutterstock.runOnMainSync
import com.serapbercin.shutterstock.ui.categories.data.CategoriesFormData
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CategoriesActivityTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<CategoriesActivity>(CategoriesActivity::class.java, true)


    @Test
    fun showCategoryList_whenUpdateCategoryListIsCalled() {
        val categories = createCategoriesResponse("categories_response_success.json")
        val activity = activityTestRule.activity
        runOnMainSync { activity.showCategories(categories.categories) }
        Espresso.onView((ViewMatchers.withId(R.id.rv_categories)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun showProgress_whenShowProgressIsCalled_thenShowProgressDialog() {
        runOnMainSync { activityTestRule.activity.showDialog() }
        Espresso.onView(ViewMatchers.withId(R.id.pb_load_categories))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun hideProgress_whenHideProgressIsCalledAfterShowProgress_thenHideProgressDialog() {
        runOnMainSync { activityTestRule.activity.showDialog() }
        runOnMainSync { activityTestRule.activity.hideDialog() }
        Espresso.onView(ViewMatchers.withId(R.id.pb_load_categories))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }


    @Test
    fun categoriesItemClick() {

        val categories = createCategoriesResponse("categories_response_success.json")
        val activity = activityTestRule.activity
        runOnMainSync { activity.showCategories(categories.categories) }

        val values = activity.listItemClicks().test().awaitCount(1).values()
        Espresso.onView(withIdHasParentId(R.id.rv_categories, R.id.rl_root_view)).perform(ViewActions.click())
        Assert.assertEquals(values[0], "2")
    }


    private fun createCategoriesResponse(fileName: String):
            CategoriesFormData = fileName parseFile gsonUpperCamel

}