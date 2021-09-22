package com.gno.github.repoviewer

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gno.github.repoviewer.di.component.ActivityComponent

class MainActivity : AppCompatActivity() {

    val activityComponent by lazy {
        applicationContext.activityComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_main)
    }
}

val Context.activityComponent: ActivityComponent
    get() = when (this) {
        is MainActivity -> activityComponent
        else -> appComponent.activityComponent().context(this).build()
    }
