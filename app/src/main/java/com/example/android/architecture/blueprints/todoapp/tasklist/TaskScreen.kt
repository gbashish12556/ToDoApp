package com.example.android.architecture.blueprints.todoapp.tasklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.source.Task
import com.example.android.architecture.blueprints.todoapp.utils.TasksTopAppBar

@Composable
fun TaskScreen(
    taskViewModel: TaskScreenViewModel = hiltViewModel(),
    onTaskClick: (Task) -> Unit,
    onAddTask: () -> Unit,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TasksTopAppBar(
                openDrawer = openDrawer,
//                onFilterAllTasks = { viewModel.setFiltering(ALL_TASKS) },
//                onFilterActiveTasks = { viewModel.setFiltering(ACTIVE_TASKS) },
//                onFilterCompletedTasks = { viewModel.setFiltering(COMPLETED_TASKS) },
//                onClearCompletedTasks = { viewModel.clearCompletedTasks() },
//                onRefresh = { viewModel.refresh() }
            )
        },
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddTask() }) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.add_task))
            }
        }
    ) { paddingValues ->
        var uistate = taskViewModel.uiState.collectAsState().value
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            uistate.tasks.let { list ->
                TaskList(list = list, onTaskClick)
            }
            if (uistate.isLoading) {
                CircularProgressIndicator()
            }
            if (uistate.isLoadingFailed) {
                Text(text = uistate.errorMessage)
            }
        }
    }
}

@Composable
fun TaskList(list: List<Task>, onTaskClick: (Task) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(list) { task ->
            TaskItem(task = task, onTaskClick)
        }
    }
}

@Composable
fun TaskItem(task: Task, onTaskClick: (Task) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clickable { onTaskClick(task) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            var isClicked = remember {
                mutableStateOf(task.isCompleted)
            }
            Spacer(modifier = Modifier.width(10.dp))
            CheckBoxItem(
                isClicked = isClicked.value,
                onCheckBoxClick = { bool -> isClicked.value = bool })
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = task.title)
        }

        Divider(color = Color.Blue, thickness = 1.dp)
    }
}

@Composable
fun CheckBoxItem(isClicked: Boolean, onCheckBoxClick: (Boolean) -> Unit) {

    Checkbox(
        checked = isClicked,
        modifier = Modifier
            .size(10.dp)
            .padding(5.dp),
        onCheckedChange = { onCheckBoxClick(it) },
    )
}