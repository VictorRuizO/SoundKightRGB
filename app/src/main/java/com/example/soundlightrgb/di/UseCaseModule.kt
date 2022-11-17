package com.example.soundlightrgb.di

import android.graphics.Color
import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.model.DeviceResponse
import com.example.soundlightrgb.domain.model.VariableType
import com.example.soundlightrgb.domain.usecase.*
import com.example.soundlightrgb.view.model.ModeItemModel
import com.example.soundlightrgb.view.model.VariableItemModel
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
@Qualifier annotation class SaveDataSetup
@Qualifier annotation class LoadDataMemory
@Qualifier annotation class GetModesMemory

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @PowerDevice
    @Binds
    abstract fun bindSetPowerDeviceUseCase(imp: SetPowerDeviceUseCase): UseCase<Boolean, DeviceResponse<Boolean>>

    @Binds
    abstract fun bindGetVariableDeviceUseCase(imp: GetVariablesDeviceUseCase): UseCase<Any, DeviceResponse<List<@JvmSuppressWildcards Pair<VariableType, Double>>>>

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

    @SaveDataSetup
    @Binds
    abstract fun bindSaveVariableSetupUseCase(imp: SaveVariableSetupDataUseCase): UseCase<List<@JvmSuppressWildcards VariableItemModel>, Boolean>

    @Binds
    abstract fun bindLoadVariablesUseCase(imp: LoadVariablesUseCase): UseCase<Any, List<@JvmSuppressWildcards VariableItemModel>?>

    @Binds
    abstract fun bindSaveTokenUseCase(imp: SaveTokenUseCase): UseCase<String, Boolean>

    @Binds
    abstract fun binLoadTokenUseCase(imp: LoadTokenUseCase): UseCase<Any, String?>

    @Binds
    abstract fun bindSaveModesUseCase(imp: SaveModesUseCase): UseCase<List<@JvmSuppressWildcards ModeItemModel>, Boolean>

    @Binds
    abstract fun bindLoadModesUseCase(imp: LoadModesUseCase): UseCase<Any, List<@JvmSuppressWildcards ModeItemModel>?>

    @LoadDataMemory
    @Binds
    abstract fun bindLoadVariablesToMemoryUseCase(imp: LoadVariablesToMemoryUseCase): UseCase<Any, Boolean>

}