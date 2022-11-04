package com.example.soundlightrgb.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.model.DeviceResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mocks = DeviceResponseMocks()

    private val getVariablesDeviceUseCase: UseCase<Any, DeviceResponse<List<Pair<String, Double>>>> = mockk()
    private val setPowerDeviceUseCase = mock<UseCase<Boolean, DeviceResponse<Boolean>>>()
    private val setColorDeviceUseCase = mock<UseCase<Int, DeviceResponse<Int>>>()
    private val setBrightnessDeviceUseCase = mock<UseCase<Double, DeviceResponse<Double>>>()
    private val setSpeedDeviceUseCase = mock<UseCase<Double, DeviceResponse<Double>>>()
    private val setVolumeDeviceUseCase = mock<UseCase<Double, DeviceResponse<Double>>>()
    private val setModeDeviceUseCase = mock<UseCase<Int, DeviceResponse<Int>>>()
    private val setWhitePowerDeviceUseCase = mock<UseCase<Boolean, DeviceResponse<Boolean>>>()
    private val setWhiteBrightnessDeviceUseCase = mock<UseCase<Double, DeviceResponse<Double>>>()

    private val viewModel = MainViewModel(
        getVariablesDeviceUseCase,
        setPowerDeviceUseCase,
        setColorDeviceUseCase,
        setBrightnessDeviceUseCase,
        setSpeedDeviceUseCase,
        setVolumeDeviceUseCase,
        setModeDeviceUseCase,
        setWhitePowerDeviceUseCase,
        setWhiteBrightnessDeviceUseCase
    )


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get all variables when init viewmodel and is fail`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))

        coEvery {
            getVariablesDeviceUseCase.execute()
        } returns mocks.getAllVariablesDeviceResponseMocksFail

        val warningObserver = Observer<String?> {}
        viewModel.warningSnackbar.observeForever(warningObserver)

        // Act
        viewModel.init()

        //Assert
        assertEquals(mocks.getAllVariablesDeviceResponseMocksFail.errorMessage, viewModel.warningSnackbar.value)
        assertEquals(false, viewModel.loader.value)
    }

    @Test
    fun sendPower() {
    }

    @Test
    fun sendColor() {
    }

    @Test
    fun sendBrightness() {
    }

    @Test
    fun sendSpeed() {
    }

    @Test
    fun sendVolume() {
    }

    @Test
    fun sendMode() {
    }

    @Test
    fun sendWhitePower() {
    }

    @Test
    fun sendWhiteBrightness() {
    }
}