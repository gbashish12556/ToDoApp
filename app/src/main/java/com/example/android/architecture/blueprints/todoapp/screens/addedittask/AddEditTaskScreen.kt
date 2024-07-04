package com.example.android.architecture.blueprints.todoapp.screens.addedittask

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.architecture.blueprints.todoapp.utils.AddEditTaskTopAppBar

@Composable
fun AddEditTaskScreen(
    @StringRes title: Int,
    onTaskUpdated: () -> Unit,
    onBack: () -> Unit,
    addEditTaskViewModel: AddEditTaskViewModel = hiltViewModel()
) {
    var uiState = addEditTaskViewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AddEditTaskTopAppBar(title = title, onBack = onBack)
        },
    ) { paddingValues ->

        var uistate = addEditTaskViewModel.uiState.collectAsState().value

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            
            uistate.task.title.let {
                MainComponent(
                    uiState = uiState.value,
                    updateTitle = { title -> addEditTaskViewModel.updateTitle(title) },
                    updateContent = { content -> addEditTaskViewModel.updateContent(content) },
                    updateTask = { addEditTaskViewModel.updateTask() }
                )
            }

            if (uistate.isLoading) {
                CircularProgressIndicator()
            }
            if (uistate.isLoadingFailed) {
                Text(text = uistate.errorMessage)
            }
        }

    }


    LaunchedEffect(uiState.value.isTaskUpdated) {
        if (uiState.value.isTaskUpdated) {
            onTaskUpdated()
        }
    }
}

@Composable
fun MainComponent(
    uiState: TaskAddEditUiState,
    updateTitle: (String) -> Unit,
    updateContent: (String) -> Unit,
    updateTask: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = uiState.task.title,
            onValueChange = { updateTitle(it) },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )

        TextField(
            value = uiState.task.content,
            onValueChange = { updateContent(it) },
            label = { Text("Content Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(
            onClick = {
                updateTask()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text("Submit")
        }
    }
}
