package com.serapbercin.shutterstock.application

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class ShutterStockApplicationTest : Application(), HasActivityInjector {
    @Inject lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
              DaggerTestApplicationComponent.create().inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}