/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp

import android.app.Activity
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailScreen
import com.example.android.architecture.blueprints.todoapp.tasklist.TaskScreen

@Composable
fun TodoNavGraph(
    navController: NavHostController = rememberNavController(),
    navActions: TodoNavigationActions = remember(navController) {
        TodoNavigationActions(navController)
    }
) {
    Surface(color = MaterialTheme.colors.background) {
        NavHost(
            navController = navController,
            startDestination = TodoDestinations.TASKS_ROUTE
        ){
            composable(
                route = TodoDestinations.TASKS_ROUTE
            ){
                TaskScreen(
                    onTaskClick = { task -> navActions.navigateToTaskDetail(task.id) }
                )
            }
            composable(route = TodoDestinations.TASK_DETAIL_ROUTE) {
                TaskDetailScreen(
                    onEditTask = { taskId ->
                        navActions.navigateToAddEditTask(R.string.edit_task, taskId)
                    },
                    onBack = { navController.popBackStack() },
                    onDeleteTask = { navActions.navigateToTasks(DELETE_RESULT_OK) }
                )
            }
        }
    }
}

const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3
