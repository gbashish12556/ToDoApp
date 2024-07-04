package com.example.android.architecture.blueprints.todoapp.screens.taskdetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.source.Task
import com.example.android.architecture.blueprints.todoapp.utils.TaskDetailTopAppBar

@Composable
fun TaskDetailScreen(
    onEditTask: (Int) -> Unit,
    onBack: () -> Unit,
    onDeleteTask: () -> Unit,
    taskDetailViewModel: TaskDetailViewModel = hiltViewModel(),
) {

    var uistate = taskDetailViewModel.uiState.collectAsState().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { onEditTask(uistate.task.id!!) }) {
                Icon(Icons.Filled.Edit, stringResource(id = R.string.edit_task))
            }
        },
        topBar = {
            TaskDetailTopAppBar(onBack = onBack, onDelete = taskDetailViewModel::deleteTask)
        },
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            uistate.task.let { task ->
                TakDetailMainComponent(task = task)
            }
            if (uistate.isLoading) {
                CircularProgressIndicator()
            }
            if (uistate.isLoadingFailed) {
                Text(text = uistate.errorMessage)
            }
        }

    }

    LaunchedEffect(uistate.isTaskDeleted) {
        if (uistate.isTaskDeleted) {
            onDeleteTask()
        }
    }

}

@Composable
fun TakDetailMainComponent(task: Task) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Title : ${task.title}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Content : ${task.content}",
            fontSize = 16.sp,
        )
    }
}

