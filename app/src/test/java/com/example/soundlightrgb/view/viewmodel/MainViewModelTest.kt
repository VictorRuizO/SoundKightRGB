package com.example.soundlightrgb.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.model.DeviceResponse
import com.example.soundlightrgb.domain.model.VariableType
import com.example.soundlightrgb.mocks.DeviceResponseMocks
import com.example.soundlightrgb.view.model.ModeItemModel
import io.mockk.*
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

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mocks = DeviceResponseMocks()

    private val getVariablesDeviceUseCase: UseCase<Any, DeviceResponse<List<Pair<VariableType, Double>>>> = mockk()
    private val setPowerDeviceUseCase = mockk<UseCase<Boolean, DeviceResponse<Boolean>>>()
    private val setColorDeviceUseCase = mockk<UseCase<Int, DeviceResponse<Int>>>()
    private val setBrightnessDeviceUseCase = mockk<UseCase<Double, DeviceResponse<Double>>>()
    private val setSpeedDeviceUseCase = mockk<UseCase<Double, DeviceResponse<Double>>>()
    private val setVolumeDeviceUseCase = mockk<UseCase<Double, DeviceResponse<Double>>>()
    private val setModeDeviceUseCase = mockk<UseCase<Int, DeviceResponse<Int>>>()
    private val setWhitePowerDeviceUseCase = mockk<UseCase<Boolean, DeviceResponse<Boolean>>>()
    private val setWhiteBrightnessDeviceUseCase = mockk<UseCase<Double, DeviceResponse<Double>>>()
    private val loadVariablesToMemoryUseCase = mockk<UseCase<Any, Boolean>>()
    private val getModesSavedUseCase = mockk<UseCase<Any, List<ModeItemModel>?>>()

    private val viewModel = MainViewModel(
        getVariablesDeviceUseCase,
        setPowerDeviceUseCase,
        setColorDeviceUseCase,
        setBrightnessDeviceUseCase,
        setSpeedDeviceUseCase,
        setVolumeDeviceUseCase,
        setModeDeviceUseCase,
        setWhitePowerDeviceUseCase,
        setWhiteBrightnessDeviceUseCase,
        loadVariablesToMemoryUseCase,
        getModesSavedUseCase,
    )


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get all variables when init viewmodel and is fail`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))

        coEvery {
            getVariablesDeviceUseCase.execute()
        } returns mocks.getAllVariablesDeviceResponseMocksFail

        coEvery {
            loadVariablesToMemoryUseCase.execute()
        } returns true

        coEvery {
            getModesSavedUseCase.execute()
        } returns mocks.getModesSaved

        val warningObserver = Observer<String?> {}
        viewModel.warningSnackbar.observeForever(warningObserver)

        // Act
        viewModel.init()

        //Assert
        assertEquals(mocks.getAllVariablesDeviceResponseMocksFail.errorMessage, viewModel.warningSnackbar.value)
        assertEquals(false, viewModel.loader.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get all variables when init viewmodel and is success`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))

        coEvery {
            getVariablesDeviceUseCase.execute()
        } returns mocks.getAllVariablesDeviceResponseMocks

        coEvery {
            loadVariablesToMemoryUseCase.execute()
        } returns true

        coEvery {
            getModesSavedUseCase.execute()
        } returns mocks.getModesSaved

        val warningObserver = Observer<String?> {}
        viewModel.warningSnackbar.observeForever(warningObserver)

        // Act
        viewModel.init()

        //Assert
        assertEquals(mocks.getAllVariablesDeviceResponseMocks.value?.find { it.first == VariableType.POWER_VAR }?.second == 1.0, viewModel.powerDevice.value)
        assertEquals(mocks.getAllVariablesDeviceResponseMocks.value?.find { it.first == VariableType.BRIGHTNESS_VAR }?.second, viewModel.brightness.value)
        assertEquals(mocks.getAllVariablesDeviceResponseMocks.value?.find { it.first == VariableType.SPEED_VAR }?.second, viewModel.speed.value)
        assertEquals(mocks.getAllVariablesDeviceResponseMocks.value?.find { it.first == VariableType.VOLUME_VAR }?.second, viewModel.volume.value)
        assertEquals(mocks.getAllVariablesDeviceResponseMocks.value?.find { it.first == VariableType.MODE_VAR }?.second?.toInt(), viewModel.mode.value)
        assertEquals(mocks.getAllVariablesDeviceResponseMocks.value?.find { it.first == VariableType.WHITE_POWER_VAR }?.second == 1.0, viewModel.whitePower.value)
        assertEquals(mocks.getAllVariablesDeviceResponseMocks.value?.find { it.first == VariableType.WHITE_BRIGHTNESS_VAR }?.second, viewModel.whiteBrightness.value)
        assertEquals(false, viewModel.loader.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set power device when is success`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = true
        coEvery {
            setPowerDeviceUseCase.execute(value)
        } returns mocks.setPowerDeviceMock

        // Act
        viewModel.sendPower(value)

        // Assert
        assertEquals(mocks.setPowerDeviceMock.value, viewModel.powerDevice.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set power device when is fail`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = true
        coEvery {
            setPowerDeviceUseCase.execute(value)
        } returns mocks.setPowerDeviceMockFail

        // Act
        viewModel.sendPower(value)

        // Assert
        assertEquals(null, viewModel.powerDevice.value)
        assertEquals(mocks.setPowerDeviceMockFail.errorMessage, viewModel.warningSnackbar.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set color device when is success`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = 168972667
        coEvery {
            setColorDeviceUseCase.execute(value)
        } returns mocks.setColorDeviceMock

        // Act
        viewModel.sendColor(value)

        // Assert
        assertEquals(mocks.setColorDeviceMock.value, viewModel.color.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set color device when is fail`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = 168972667
        coEvery {
            setColorDeviceUseCase.execute(value)
        } returns mocks.setColorDeviceMockFail

        // Act
        viewModel.sendColor(value)

        // Assert
        assertEquals(null, viewModel.color.value)
        assertEquals(mocks.setColorDeviceMockFail.errorMessage, viewModel.warningSnackbar.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set brightness device when is success`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = 100
        coEvery {
            setBrightnessDeviceUseCase.execute(value.toDouble())
        } returns mocks.setBrightnessDeviceMock

        // Act
        viewModel.sendBrightness(value)

        // Assert
        assertEquals(mocks.setBrightnessDeviceMock.value, viewModel.brightness.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set brightness device when is fail`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = 100
        coEvery {
            setBrightnessDeviceUseCase.execute(value.toDouble())
        } returns mocks.setBrightnessDeviceMockFail

        // Act
        viewModel.sendBrightness(value)

        // Assert
        assertEquals(null, viewModel.brightness.value)
        assertEquals(mocks.setBrightnessDeviceMockFail.errorMessage, viewModel.warningSnackbar.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set speed device when is success`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = 100
        coEvery {
            setSpeedDeviceUseCase.execute(value.toDouble())
        } returns mocks.setSpeedDeviceMock

        // Act
        viewModel.sendSpeed(value)

        // Assert
        assertEquals(mocks.setSpeedDeviceMock.value, viewModel.speed.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set speed device when is fail`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = 100
        coEvery {
            setSpeedDeviceUseCase.execute(value.toDouble())
        } returns mocks.setSpeedDeviceMockFail

        // Act
        viewModel.sendSpeed(value)

        // Assert
        assertEquals(null, viewModel.speed.value)
        assertEquals(mocks.setSpeedDeviceMockFail.errorMessage, viewModel.warningSnackbar.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set volume device when is success`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = 100
        coEvery {
            setVolumeDeviceUseCase.execute(value.toDouble())
        } returns mocks.setvolumeDeviceMock

        // Act
        viewModel.sendVolume(value)

        // Assert
        assertEquals(mocks.setvolumeDeviceMock.value, viewModel.volume.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set volume device when is fail`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = 100
        coEvery {
            setVolumeDeviceUseCase.execute(value.toDouble())
        } returns mocks.setVolumeDeviceMockFail

        // Act
        viewModel.sendVolume(value)

        // Assert
        assertEquals(null, viewModel.volume.value)
        assertEquals(mocks.setVolumeDeviceMockFail.errorMessage, viewModel.warningSnackbar.value)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set mode device when is success`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = 1
        coEvery {
            setModeDeviceUseCase.execute(value)
        } returns mocks.setModeDeviceMock

        // Act
        viewModel.sendMode(value)

        // Assert
        assertEquals(null, viewModel.mode.value)
        coVerify (exactly = 1) { setModeDeviceUseCase.execute(value) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set mode device when is fail`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = 1
        coEvery {
            setModeDeviceUseCase.execute(value)
        } returns mocks.setModeDeviceMockFail

        // Act
        viewModel.sendMode(value)

        // Assert
        assertEquals(null, viewModel.mode.value)
        assertEquals(mocks.setModeDeviceMockFail.errorMessage, viewModel.warningSnackbar.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set white power device when is success`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = false
        coEvery {
            setWhitePowerDeviceUseCase.execute(value)
        } returns mocks.setWhitePowerDeviceMock

        // Act
        viewModel.sendWhitePower(value)

        // Assert
        assertEquals(mocks.setWhitePowerDeviceMock.value, viewModel.whitePower.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set white power device when is fail`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = false
        coEvery {
            setWhitePowerDeviceUseCase.execute(value)
        } returns mocks.setWhitePowerDeviceMockFail

        // Act
        viewModel.sendWhitePower(value)

        // Assert
        assertEquals(null, viewModel.whitePower.value)
        assertEquals(mocks.setWhitePowerDeviceMockFail.errorMessage, viewModel.warningSnackbar.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set white brightness device when is success`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = 200.0
        coEvery {
            setWhiteBrightnessDeviceUseCase.execute(value)
        } returns mocks.setWhiteBrightnessDeviceMock

        // Act
        viewModel.sendWhiteBrightness(value)

        // Assert
        assertEquals(mocks.setWhiteBrightnessDeviceMock.value, viewModel.whiteBrightness.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Set white brightness device when is fail`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = 200.0
        coEvery {
            setWhiteBrightnessDeviceUseCase.execute(value)
        } returns mocks.setWhiteBrightnessDeviceMockFail

        // Act
        viewModel.sendWhiteBrightness(value)

        // Assert
        assertEquals(null, viewModel.whiteBrightness.value)
        assertEquals(mocks.setWhiteBrightnessDeviceMockFail.errorMessage, viewModel.warningSnackbar.value)
    }
}