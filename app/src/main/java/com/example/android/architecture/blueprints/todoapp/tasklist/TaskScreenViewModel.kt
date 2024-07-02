package com.example.android.architecture.blueprints.todoapp.tasklist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.architecture.blueprints.todoapp.common.Resource
import com.example.android.architecture.blueprints.todoapp.usecases.GetTaskListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewModel @Inject constructor(
    val getTaskListUseCase: GetTaskListUseCase,
            savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _uiState = MutableStateFlow(TaskListUiState())
    var uiState:StateFlow<TaskListUiState> = _uiState.asStateFlow()

    init {
        refreshTask()
    }

    fun refreshTask(){

        viewModelScope.launch {

            getTaskListUseCase(All()).collect{result->
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
                        _uiState.update {state->
                            result.data.let { data->
                                state.copy(tasks = data!!)
                            }
                        }
                    }
                }
            }

        }
    }
}