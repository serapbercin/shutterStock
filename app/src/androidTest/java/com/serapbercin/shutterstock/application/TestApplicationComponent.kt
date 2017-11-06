package com.serapbercin.shutterstock.application

import com.serapbercin.shutterstock.module.UtilityModuleTest
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class,
        UtilityModuleTest::class))
interface TestApplicationComponent {

    fun inject(shutterStockApplicationTest: ShutterStockApplicationTest)
}