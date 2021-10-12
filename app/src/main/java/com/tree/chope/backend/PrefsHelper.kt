package com.tree.chope.backend

import android.content.Context

object PrefsHelper{
    lateinit var instance: PreferenceHelper

    fun init(context: Context) {
        instance = PreferenceHelper(context.getSharedPreferences(
            "com.tree.chope",
            Context.MODE_PRIVATE
        ))
    }
}