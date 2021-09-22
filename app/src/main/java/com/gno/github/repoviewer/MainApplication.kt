package com.gno.github.repoviewer

import android.app.Application
import android.content.Context
import com.gno.github.repoviewer.di.component.AppComponent
import com.gno.github.repoviewer.di.component.DaggerAppComponent

class MainApplication : Application() {
    val appComponent by lazy { DaggerAppComponent.builder().context(this).build() }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApplication -> appComponent
        else -> this.applicationContext.appComponent
    }
