package com.serapbercin.shutterstock

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner
import com.serapbercin.shutterstock.application.ShutterStockApplicationTest

class TestAndroidJUnitRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?,
                                context: Context?): Application {
        return super.newApplication(cl, ShutterStockApplicationTest::class.java.name, context)
    }
}