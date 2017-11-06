package com.serapbercin.shutterstock
import android.support.test.InstrumentationRegistry

fun runOnMainSync(action: () -> Unit) {
    InstrumentationRegistry.getInstrumentation().runOnMainSync(action)
}