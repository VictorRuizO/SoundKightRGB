package com.example.soundlightrgb.di

import android.graphics.Color
import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.model.DeviceResponse
import com.example.soundlightrgb.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier annotation class PowerDevice
@Qualifier annotation class ColorDevice
@Qualifier annotation class BightnessDevice
@Qualifier annotation class SpeedDevice
@Qualifier annotation class VolumeDevice
@Qualifier annotation class ModeDevice
@Qualifier annotation class WhitePowerDevice
@Qualifier annotation class WhiteBrightnessDevice

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @PowerDevice
    @Binds
    abstract fun bindSetPowerDeviceUseCase(imp: SetPowerDeviceUseCase): UseCase<Boolean, DeviceResponse<@JvmSuppressWildcards Boolean>>

    @Binds
    abstract fun bindGetVariableDeviceUseCase(imp: GetVariablesDeviceUseCase): UseCase<Any, DeviceResponse<List<@JvmSuppressWildcards Pair<String, Double>>>>

    @BightnessDevice
    @Binds
    abstract fun bindSetBrightnessDeviceUseCase(imp: SetBrightnessDeviceUseCase): UseCase<Double, DeviceResponse<Double>>

    @SpeedDevice
    @Binds
    abstract fun bindSetSpeedDeviceUseCase(imp: SetSpeedDeviceUseCase): UseCase<Double, DeviceResponse<Double>>

    @VolumeDevice
    @Binds
    abstract fun bindSetVolumeDeviceUseCase(imp: SetVolumeDeviceUseCase): UseCase<Double, DeviceResponse<Double>>

    @ModeDevice
    @Binds
    abstract fun bindSetModeDeviceUseCase(imp: SetModeDeviceUseCase): UseCase<Int, DeviceResponse<Int>>

    @WhitePowerDevice
    @Binds
    abstract fun bindSetWhitePowerDeviceUseCase(imp: SetWhitePowerDeviceUseCase): UseCase<Boolean, DeviceResponse<Boolean>>

    @WhiteBrightnessDevice
    @Binds
    abstract fun bindSetWhiteBrightnessDeviceUseCase(imp: SetWhiteBrightnessDeviceUseCase): UseCase<Double, DeviceResponse<Double>>

    @ColorDevice
    @Binds
    abstract fun bindSetColorDeviceUseCase(imp: SetColorDeviceUseCase): UseCase<Int, DeviceResponse<Int>>
}