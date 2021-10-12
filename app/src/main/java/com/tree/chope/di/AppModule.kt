package com.tree.chope.di

import android.content.Context
import com.tree.chope.backend.PreferenceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) : PreferenceHelper {
        return PreferenceHelper(context.getSharedPreferences(
            "com.tree.chope", Context.MODE_PRIVATE
        ))
    }

}