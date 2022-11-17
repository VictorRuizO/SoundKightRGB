package com.example.soundlightrgb.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundlightrgb.R
import com.example.soundlightrgb.di.LoadDataMemory
import com.example.soundlightrgb.di.SaveDataSetup
import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.usecase.SaveTokenUseCase
import com.example.soundlightrgb.view.model.ModeItemModel
import com.example.soundlightrgb.view.model.VariableItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    @SaveDataSetup private val saveVariableSetupDataUseCase: UseCase<List<@JvmSuppressWildcards VariableItemModel>, Boolean>,
    private val loadVariablesUseCase: UseCase<Any, List<@JvmSuppressWildcards VariableItemModel>?>,
    private val saveTokenUseCase: UseCase<String, Boolean>,
    private val loadTokenUseCase: UseCase<Any, String?>,
    private val saveModesUseCase: UseCase<List<@JvmSuppressWildcards ModeItemModel>, Boolean>,
    private val loadModesUseCase: UseCase<Any, List<@JvmSuppressWildcards ModeItemModel>?>,
    @LoadDataMemory private val loadVariablesToMemoryUseCase: UseCase<Any, Boolean>,
): ViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _variables = MutableLiveData<List<VariableItemModel>>()
    val variables: LiveData<List<VariableItemModel>> = _variables

    private val _modes = MutableLiveData<List<ModeItemModel>>()
    val modes: LiveData<List<ModeItemModel>> = _modes

    private val _loaderVariables = MutableLiveData<Boolean>()
    val loaderVariables: LiveData<Boolean> = _loaderVariables

    private val _loaderModes = MutableLiveData<Boolean>()
    val loaderModes: LiveData<Boolean> = _loaderModes

    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean> = _loader

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    private val _warning = MutableLiveData<Int>()
    val warning: LiveData<Int> = _warning

    fun init() {
        _state.postValue(true)
        _loaderVariables.postValue(true)
        _loaderModes.postValue(true)
        viewModelScope.launch {
            async {
                // delay(2000)
                val tk = loadTokenUseCase.execute()
                tk.let {
                    _token.postValue(it)
                }
            }
            async {
                // delay(3000)
                val vars = loadVariablesUseCase.execute()
                vars?.let {
                    _variables.postValue(it)
                }
                _loaderVariables.postValue(false)
            }
            async {
                // delay(1000)
                val modes = loadModesUseCase.execute()
                modes?.let {
                    _modes.postValue(it)
                }
                _loaderModes.postValue(false)
            }
        }
    }

    fun saveData(token: String, vars: List<VariableItemModel>, modes: List<ModeItemModel>) = viewModelScope.launch {
        _loader.postValue(true)
        if (token.isNotEmpty() && vars.none { it.value.isEmpty() }) {
            val tkResult = async {
                saveTokenUseCase.execute(token)
            }
            val varsResult = async {
                saveVariableSetupDataUseCase.execute(vars)
            }
            val modesResult = async {
                saveModesUseCase.execute(modes)
            }

            if (tkResult.await() && varsResult.await() && modesResult.await()) {
                loadVariablesToMemoryUseCase.execute()
                _state.postValue(false)
            } else {
                _warning.postValue(R.string.error_saving_data)
            }
        } else {
            _warning.postValue(R.string.error_empty_saving_data)
        }
        _loader.postValue(false)
    }
}