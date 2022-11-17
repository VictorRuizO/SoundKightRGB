package com.example.soundlightrgb.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.soundlightrgb.di.LoadDataMemory
import com.example.soundlightrgb.di.SaveDataSetup
import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.mocks.SetupMocks
import com.example.soundlightrgb.view.model.ModeItemModel
import com.example.soundlightrgb.view.model.VariableItemModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.mock

class SetupViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val saveVariableSetupDataUseCase = mockk<UseCase<List<VariableItemModel>, Boolean>>()
    private val loadVariablesUseCase = mockk<UseCase<Any, List<VariableItemModel>?>>()
    private val saveTokenUseCase = mockk<UseCase<String, Boolean>>()
    private val loadTokenUseCase = mockk<UseCase<Any, String?>>()
    private val saveModesUseCase = mockk<UseCase<List<ModeItemModel>, Boolean>>()
    private val loadModesUseCase = mockk<UseCase<Any, List<ModeItemModel>?>>()
    private val loadVariablesToMemoryUseCase = mockk<UseCase<Any, Boolean>>()

    private val viewModel = SetupViewModel(
        saveVariableSetupDataUseCase,
        loadVariablesUseCase,
        saveTokenUseCase,
        loadTokenUseCase,
        saveModesUseCase,
        loadModesUseCase,
        loadVariablesToMemoryUseCase,
    )

    private val mocks = SetupMocks()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get all variables when init viewmodel and is success`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))

        coEvery {
            loadTokenUseCase.execute()
        } returns mocks.token
        coEvery {
            loadVariablesUseCase.execute()
        } returns mocks.listVariableItemModel
        coEvery {
            loadModesUseCase.execute()
        } returns mocks.listModeItemModel

        // Act
        viewModel.init()

        // Assert
        assertEquals(mocks.token, viewModel.token.value)
        assertEquals(mocks.listVariableItemModel, viewModel.variables.value)
        assertEquals(mocks.listModeItemModel, viewModel.modes.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get all variables when init viewmodel and token fails`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val value = null

        coEvery {
            loadTokenUseCase.execute()
        } returns value
        coEvery {
            loadVariablesUseCase.execute()
        } returns mocks.listVariableItemModel
        coEvery {
            loadModesUseCase.execute()
        } returns mocks.listModeItemModel

        // Act
        viewModel.init()

        // Assert
        assertEquals(value, viewModel.token.value)
        assertEquals(mocks.listVariableItemModel, viewModel.variables.value)
        assertEquals(mocks.listModeItemModel, viewModel.modes.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get all variables when init viewmodel and variables fail`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))

        val value = null

        coEvery {
            loadTokenUseCase.execute()
        } returns mocks.token
        coEvery {
            loadVariablesUseCase.execute()
        } returns value
        coEvery {
            loadModesUseCase.execute()
        } returns mocks.listModeItemModel

        // Act
        viewModel.init()

        // Assert
        assertEquals(mocks.token, viewModel.token.value)
        assertEquals(value, viewModel.variables.value)
        assertEquals(mocks.listModeItemModel, viewModel.modes.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get all variables when init viewmodel and modes fail`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))

        val value = null

        coEvery {
            loadTokenUseCase.execute()
        } returns mocks.token
        coEvery {
            loadVariablesUseCase.execute()
        } returns mocks.listVariableItemModel
        coEvery {
            loadModesUseCase.execute()
        } returns value

        // Act
        viewModel.init()

        // Assert
        assertEquals(mocks.token, viewModel.token.value)
        assertEquals(mocks.listVariableItemModel, viewModel.variables.value)
        assertEquals(null, viewModel.modes.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Save data when all is success`() = runTest {
        // Arrange
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))

        val tokenResponse = true
        val varResponse = true
        val modesResponse = true

        coEvery {
            saveTokenUseCase.execute(mocks.token)
        } returns tokenResponse
        coEvery {
            saveVariableSetupDataUseCase.execute(mocks.listVariableItemModel)
        } returns varResponse
        coEvery {
            saveModesUseCase.execute(mocks.listModeItemModel)
        } returns modesResponse
        coEvery {
            loadVariablesToMemoryUseCase.execute()
        } returns true

        // Act
        viewModel.saveData(
            mocks.token,
            mocks.listVariableItemModel,
            mocks.listModeItemModel
        )

        // Assert
        coVerify { loadVariablesToMemoryUseCase.execute() }
        assertEquals(false, viewModel.state.value)
    }
}