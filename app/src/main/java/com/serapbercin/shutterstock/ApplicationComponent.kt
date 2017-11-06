package com.serapbercin.shutterstock

import com.serapbercin.shutterstock.data.DataModule
import com.serapbercin.shutterstock.module.UtilityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(ActivitiesModule::class, AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class, UtilityModule::class,
        DataModule::class))
interface ApplicationComponent {


    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun shutterStockApplication(shutterStockApplication: ShutterStockApplication): Builder
    }

    fun inject(shutterStockApplication: ShutterStockApplication)
}