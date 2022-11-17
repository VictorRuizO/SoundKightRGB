package com.example.soundlightrgb.view.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundlightrgb.di.*
import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.model.DeviceResponse
import com.example.soundlightrgb.domain.model.VariableType
import com.example.soundlightrgb.util.value
import com.example.soundlightrgb.view.model.ModeItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getVariablesDeviceUseCase: UseCase<Any, DeviceResponse<List<@JvmSuppressWildcards Pair<VariableType, Double>>>>,
    @PowerDevice private val setPowerDeviceUseCase: UseCase<Boolean, DeviceResponse<Boolean>>,
    @ColorDevice private val setColorDeviceUseCase: UseCase<Int, DeviceResponse<Int>>,
    @BightnessDevice private val setBrightnessDeviceUseCase: UseCase<Double, DeviceResponse<Double>>,
    @SpeedDevice private val setSpeedDeviceUseCase: UseCase<Double, DeviceResponse<Double>>,
    @VolumeDevice private val setVolumeDeviceUseCase: UseCase<Double, DeviceResponse<Double>>,
    @ModeDevice private val setModeDeviceUseCase: UseCase<Int, DeviceResponse<Int>>,
    @WhitePowerDevice private val setWhitePowerDeviceUseCase: UseCase<Boolean, DeviceResponse<Boolean>>,
    @WhiteBrightnessDevice private val setWhiteBrightnessDeviceUseCase: UseCase<Double, DeviceResponse<Double>>,
    @LoadDataMemory private val loadVariablesToMemoryUseCase: UseCase<Any, Boolean>,
    private val getModesSavedUseCase: UseCase<Any, List<@JvmSuppressWildcards ModeItemModel>?>
) : ViewModel() {
    private val _powerDevice = MutableLiveData<Boolean>()
    val powerDevice: LiveData<Boolean> = _powerDevice

    private val _brightness = MutableLiveData<Double>()
    val brightness: LiveData<Double> = _brightness

    private val _speed = MutableLiveData<Double>()
    val speed: LiveData<Double> = _speed

    private val _volume = MutableLiveData<Double>()
    val volume: LiveData<Double> = _volume

    private val _mode = MutableLiveData<Int>()
    val mode: LiveData<Int> = _mode

    private val _whitePower = MutableLiveData<Boolean>()
    val whitePower: LiveData<Boolean> = _whitePower

    private val _whiteBrightness = MutableLiveData<Double>()
    val whiteBrightness: LiveData<Double> = _whiteBrightness

    private val _color = MutableLiveData<Int>()
    val color: LiveData<Int> = _color

    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean> = _loader

    private val _goToSetup = MutableLiveData<Boolean>()
    val goToSetup: LiveData<Boolean> = _goToSetup

    private val _warningSnackbar = MutableLiveData<String?>()
    val warningSnackbar: LiveData<String?> = _warningSnackbar

    private val _listModes = MutableLiveData<List<ModeItemModel>>()
    val listModes: LiveData<List<ModeItemModel>> = _listModes

    fun init() {
        viewModelScope.launch {
            _loader.postValue(true)
            // delay(5000)
            if (!loadVariablesToMemoryUseCase.execute()){
                _goToSetup.postValue(true)
                return@launch
            }
            async {
                val modes = getModesSavedUseCase.execute()
                modes?.let {
                    _listModes.postValue(it)
                }
            }
            val response = getVariablesDeviceUseCase.execute()
            if (response.isSuccessful){
                response.value?.forEach {
                    when(it.first) {
                        VariableType.POWER_VAR -> _powerDevice.postValue(it.second == 1.0)
                        VariableType.BRIGHTNESS_VAR -> _brightness.postValue(it.second.value())
                        VariableType.SPEED_VAR -> _speed.postValue(it.second.value())
                        VariableType.VOLUME_VAR -> _volume.postValue(it.second.value())
                        VariableType.MODE_VAR -> _mode.postValue(it.second.value().toInt())
                        VariableType.WHITE_POWER_VAR -> _whitePower.postValue(it.second == 1.0)
                        VariableType.WHITE_BRIGHTNESS_VAR -> _whiteBrightness.postValue(it.second.value())
                        else -> { }
                    }
                }
            } else {
                _warningSnackbar.postValue(response.errorMessage)
                _powerDevice.postValue(POWER_INITIAL_VALUE)
                _brightness.postValue(BRIGHTNESS_INITIAL_VALUE)
                _speed.postValue(SPEDD_INITIAL_VALUE)
                _volume.postValue(VOLUME_INITIAL_VALUE)
                _mode.postValue(MODE_INITIAL_VALUE)
                _whitePower.postValue(WHITE_POWER_INITIAL_VALUE)
                _whiteBrightness.postValue(WHITE_BRIGHTNESS_INITIAL_VALUE)
            }
            _loader.postValue(false)
        }
    }

    fun sendPower(state: Boolean){
        viewModelScope.launch {
            val response = setPowerDeviceUseCase.execute(state)
            if (response.isSuccessful){
                _powerDevice.postValue(state)
            } else {
                _powerDevice.postValue(_powerDevice.value)
                _warningSnackbar.postValue(response.errorMessage)
            }
        }
    }

    fun sendColor(value: Int){
        viewModelScope.launch {
            val response = setColorDeviceUseCase.execute(value)
            if (response.isSuccessful){
                _color.postValue(value)
            } else {
                _color.postValue(_color.value)
                 _warningSnackbar.postValue(response.errorMessage)
            }
        }
    }

    fun sendBrightness(value: Int) {
        viewModelScope.launch {
            val response = setBrightnessDeviceUseCase.execute(value.toDouble())
            if (response.isSuccessful){
                _brightness.postValue(value.toDouble())
            } else {
                _brightness.postValue(_brightness.value)
                _warningSnackbar.postValue(response.errorMessage)
            }
        }
    }

    fun sendSpeed(value: Int) {
        viewModelScope.launch {
            val response = setSpeedDeviceUseCase.execute(value.toDouble())
            if (response.isSuccessful){
                _speed.postValue(value.toDouble())
            } else {
                _speed.postValue(_speed.value)
                _warningSnackbar.postValue(response.errorMessage)
            }
        }
    }

    fun sendVolume(value: Int) {
        viewModelScope.launch {
            val response = setVolumeDeviceUseCase.execute(value.toDouble())
            if (response.isSuccessful){
                _volume.postValue(value.toDouble())
            } else {
                _volume.postValue(_volume.value)
                _warningSnackbar.postValue(response.errorMessage)
            }
        }
    }

    fun sendMode(value: Int) {
        viewModelScope.launch {
            val response = setModeDeviceUseCase.execute(value)
            if (!response.isSuccessful){
                _mode.postValue(_mode.value)
                _warningSnackbar.postValue(response.errorMessage)
            }
        }
    }

    fun sendWhitePower(value: Boolean) {
        viewModelScope.launch {
            val response = setWhitePowerDeviceUseCase.execute(value)
            if (response.isSuccessful){
                _whitePower.postValue(value)
            } else {
                _whitePower.postValue(_whitePower.value)
                _warningSnackbar.postValue(response.errorMessage)
            }
        }
    }

    fun sendWhiteBrightness(value: Double) {
        viewModelScope.launch {
            val response = setWhiteBrightnessDeviceUseCase.execute(value)
            if (response.isSuccessful){
                _whiteBrightness.postValue(value)
            } else {
                _whiteBrightness.postValue(_whiteBrightness.value)
                _warningSnackbar.postValue(response.errorMessage)
            }
        }
    }

    companion object {
        private const val POWER_INITIAL_VALUE = false
        private const val BRIGHTNESS_INITIAL_VALUE = 0.0
        private const val SPEDD_INITIAL_VALUE = 0.0
        private const val VOLUME_INITIAL_VALUE = 0.0
        private const val MODE_INITIAL_VALUE = 0
        private const val WHITE_POWER_INITIAL_VALUE = false
        private const val WHITE_BRIGHTNESS_INITIAL_VALUE = 0.0
    }
}