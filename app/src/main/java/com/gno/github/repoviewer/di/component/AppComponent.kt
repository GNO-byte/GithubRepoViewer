package com.gno.github.repoviewer.di.component

import android.content.Context
import com.gno.github.repoviewer.di.module.AppModule
import com.gno.github.repoviewer.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        NetworkModule::class]
)
interface AppComponent {

    fun activityComponent(): ActivityComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent

    }
}