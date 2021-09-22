package com.gno.github.repoviewer.di.component

import android.content.Context
import com.gno.github.repoviewer.di.ActivityContext
import com.gno.github.repoviewer.MainActivity
import com.gno.github.repoviewer.di.module.ActivityModule
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [ActivityModule::class]
)
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

    fun fragmentComponent(): FragmentComponent.Builder

    @Subcomponent.Builder
    interface Builder{

        @BindsInstance
        fun context(@ActivityContext context: Context): Builder

        fun build(): ActivityComponent

    }
}


