package com.example.soundlightrgb.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ActivityModule {

    @Provides
    fun provideActivity(@ApplicationContext context: ApplicationContext): Activity{
        return context as Activity
    }
}