package com.example.soundlightrgb.di

import com.example.soundlightrgb.data.remote.DeviceServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {
    private const val BASE_URL = "https://industrial.api.ubidots.com/api/v1.6/variables/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(): DeviceServices = getRetrofit().create(DeviceServices::class.java)
}