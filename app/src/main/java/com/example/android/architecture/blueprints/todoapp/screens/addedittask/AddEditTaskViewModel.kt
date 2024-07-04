package com.example.android.architecture.blueprints.todoapp.screens.addedittask

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.architecture.blueprints.todoapp.TodoDestinationsArgs
import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.data.source.Task
import com.example.android.architecture.blueprints.todoapp.usecases.AddTaskUseCase
import com.example.android.architecture.blueprints.todoapp.usecases.EditTaskUseCase
import com.example.android.architecture.blueprints.todoapp.usecases.GetTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    val getTaskUseCase: GetTaskUseCase,
    val editTaskUseCase: EditTaskUseCase,
    val addTaskUseCase: AddTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId: String? = savedStateHandle[TodoDestinationsArgs.TASK_ID_ARG]

    private var _uiState = MutableStateFlow(TaskAddEditUiState())
    var uiState: StateFlow<TaskAddEditUiState> = _uiState.asStateFlow()

    init {
        if (taskId != null) {
            getTask()
        }
    }

    fun getTask() {
        viewModelScope.launch(Dispatchers.IO) {
            getTaskUseCase(taskId?.toIntOrNull()).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isLoadingFailed = true,
                                errorMessage = result.message!!
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update { state ->
                            result.data.let { data ->
                                state.copy(isLoading = false, task = data!!)
                            }
                        }
                    }
                }
            }
        }
    }

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(task = _uiState.value.task.copy(title = title))
    }

    fun updateContent(content: String) {
        _uiState.value = _uiState.value.copy(task = _uiState.value.task.copy(content = content))
    }

    fun updateTask() {
        uiState.value.task.id?.let {
            editTask()
        } ?: kotlin.run {
            addTask()
        }
    }

    fun editTask() {
        viewModelScope.launch(Dispatchers.IO) {
            editTaskUseCase(uiState.value.task).collect { result ->
                updateState(result)
            }
        }
    }

    fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            addTaskUseCase(uiState.value.task).collect { result ->
                updateState(result)
            }
        }
    }

    fun updateState(result: Resource<Task>) {
        when (result) {
            is Resource.Success -> {
                _uiState.update { it ->
                    it.copy(isTaskUpdated = true, isLoading = false)
                }
            }
            is Resource.Loading -> {
                _uiState.update { it ->
                    it.copy(isLoading = true)
                }
            }
            is Resource.Error -> {
                _uiState.update { it ->
                    it.copy(
                        isLoading = false,
                        isLoadingFailed = true,
                        errorMessage = "Update Failed"
                    )
                }
            }
        }
    }
}