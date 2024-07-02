package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.architecture.blueprints.todoapp.TodoDestinationsArgs
import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.tasklist.All
import com.example.android.architecture.blueprints.todoapp.tasklist.TaskListUiState
import com.example.android.architecture.blueprints.todoapp.usecases.GetTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    val getTaskUseCase: GetTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val taskIdString: String? = savedStateHandle[TodoDestinationsArgs.TASK_ID_ARG]!!
    val taskId: Int = taskIdString?.toIntOrNull() ?: throw IllegalArgumentException("Invalid Task ID")

    private var _uiState = MutableStateFlow(TaskDetailUiState())
    var uiState: StateFlow<TaskDetailUiState> = _uiState.asStateFlow()

    init {
        refreshTask()
    }

    fun refreshTask(){

        viewModelScope.launch {

            getTaskUseCase(taskId).collect{ result->
                when(result){
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(isLoadingFailed = true, errorMessage = result.message!!)
                        }
                    }
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading =  true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update { state->
                            result.data.let { data->
                                state.copy(task = data!!)
                            }
                        }
                    }
                }
            }

        }
    }

}
