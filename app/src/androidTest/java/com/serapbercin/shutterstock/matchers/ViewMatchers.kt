package com.serapbercin.shutterstock.matchers


import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withParent
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher



fun withIdHasParentId(id: Int, parentId: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("with id $id and parent id $parentId")
        }

        override fun matchesSafely(item: View): Boolean {
            return withParent(withId(parentId)).matches(item) && withId(id).matches(item)
        }
    }
}

