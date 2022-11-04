package com.example.soundlightrgb.di

import com.example.soundlightrgb.data.respository.DeviceRepositoryImp
import com.example.soundlightrgb.domain.repository.DeviceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun deviceRepositoryImp(imp: DeviceRepositoryImp): DeviceRepository
}