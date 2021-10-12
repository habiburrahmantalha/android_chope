package com.tree.chope

import android.app.Application
import com.tree.chope.backend.PrefsHelper
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        PrefsHelper.init(this)
    }

}