package com.geeks.projectx.presentation.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.result.Result
import com.example.domain.usecase.DeleteTaskUseCase
import com.example.domain.usecase.GetAllTasksUseCase
import com.example.domain.usecase.GetTaskUseCase
import com.example.domain.usecase.InsertTaskUseCase
import com.example.domain.usecase.UpdateTaskUseCase
import com.geeks.projectx.presentation.model.TaskUI
import com.geeks.projectx.presentation.model.toDomain
import com.geeks.projectx.presentation.model.toUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TaskViewModel(
    private val insertTaskUseCase: InsertTaskUseCase,
    private val getAllTaskUseCase: GetAllTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTaskUseCase: GetTaskUseCase
) : ViewModel() {
    private val _insertMessageStateFlow = MutableStateFlow(String())
    val insertMessageFlow: StateFlow<String> = _insertMessageStateFlow.asStateFlow()

    private val _tasksStateFlow = MutableStateFlow<List<TaskUI>>(emptyList())
    val tasksFlow: StateFlow<List<TaskUI>> = _tasksStateFlow.asStateFlow()

    private val _saveTaskStateFlow = MutableStateFlow<Result<TaskUI>>(Result.Loading)
    val saveTaskStateFlow: StateFlow<Result<TaskUI>> = _saveTaskStateFlow.asStateFlow()

    private val _taskStateFlow = MutableStateFlow<Result<TaskUI>>(Result.Loading)
    val taskStateFlow: StateFlow<Result<TaskUI>> = _taskStateFlow.asStateFlow()

    private val _updateMessageStateFlow = MutableStateFlow(String())
    val updateMessageFlow: StateFlow<String> = _updateMessageStateFlow.asStateFlow()

    private val _loadingStateFlow = MutableStateFlow<LoadingState>(LoadingState.Loaded)
    val loadingFlow: StateFlow<LoadingState> = _loadingStateFlow.asStateFlow()

    private val _errorMessageFlow = MutableStateFlow(String())
    val errorMessageFlow: StateFlow<String> = _errorMessageFlow.asStateFlow()

    private fun <T> runLaunchIO(block: suspend CoroutineScope.() -> T) {
        _loadingStateFlow.value = LoadingState.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                block()
            }
        } catch (e: Exception) {
            _errorMessageFlow.value = "Error: ${e.localizedMessage} "
        } finally {
            _loadingStateFlow.value = LoadingState.Loaded
        }
    }

    fun loadTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = insertTaskUseCase(id)) {

                is Result.Loading -> {
                    _taskStateFlow.value = Result.Loading
                }

                is Result.Success -> {
                    _saveTaskStateFlow.value = Result.Success(result.data.toUI())
                }

                is Result.Error -> {
                    _taskStateFlow.value = Result.Error(result.message)
                }
            }
        }
    }

    fun loadTasks() {
        runLaunchIO {
            getAllTaskUseCase().onEach { tasks ->
                _tasksStateFlow.value = tasks.map { taskModel -> taskModel.toUI() }
            }.collect()
        }
    }

    fun getTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getTaskUseCase(id)) {

                is Result.Loading -> {
                    _taskStateFlow.value = Result.Loading
                }

                is Result.Success -> {
                    _taskStateFlow.value = Result.Success(result.data.toUI())
                }

                is Result.Error -> {
                    _taskStateFlow.value = Result.Error(result.message)
                }
            }
        }
    }

    suspend fun updateTask(taskUI: TaskUI) {
        val result = updateTaskUseCase.updateTask(taskUI.toDomain())
        viewModelScope.launch(Dispatchers.IO) {
            when (result) {
                is Result.Loading -> {
                    Result.Loading
                }

                is Result.Success -> {
                    updateTaskUseCase.updateTask(taskUI.toDomain())
                }

                is Result.Error -> {
                    _errorMessageFlow.value = result.message
                }

            }
        }

    }

    suspend fun deleteTask(taskUI: TaskUI) {
        val result = deleteTaskUseCase.deleteTask(taskUI.toDomain())
        when (result) {

            is Result.Loading -> {
                _taskStateFlow.value = Result.Loading
            }

            is Result.Success -> {
                deleteTaskUseCase.deleteTask(taskUI.toDomain())
                loadTasks()
            }

            is Result.Error -> {
                _taskStateFlow.value = Result.Error(result.message)

            }
        }
    }
}

sealed class LoadingState {
    data object Loading : LoadingState()
    data object Loaded : LoadingState()
    data class Error(val message: String) : LoadingState()
}