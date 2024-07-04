package com.example.android.architecture.blueprints.todoapp.screens.addedittask

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = uiState.value.task.title,
                onValueChange = { addEditTaskViewModel.updateTitle(it) },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )

            TextField(
                value = uiState.value.task.content,
                onValueChange = { addEditTaskViewModel.updateContent(it) },
                label = { Text("Content Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Button(
                onClick = {
                    addEditTaskViewModel.updateTask()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            ) {
                Text("Submit")
            }
        }
    }

    LaunchedEffect(uiState.value.isTaskUpdated) {
        if (uiState.value.isTaskUpdated) {
            onTaskUpdated()
        }
    }
}
