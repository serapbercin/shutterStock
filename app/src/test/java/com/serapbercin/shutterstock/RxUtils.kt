package com.serapbercin.shutterstock

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

fun hookTestRxJavaSchedulers() {
    RxJavaPlugins.setIoSchedulerHandler { _ -> Schedulers.trampoline() }
    RxJavaPlugins.setNewThreadSchedulerHandler { _ -> Schedulers.trampoline() }
    RxJavaPlugins.setComputationSchedulerHandler { _ -> Schedulers.trampoline() }
    RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
    RxAndroidPlugins.setMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
}
