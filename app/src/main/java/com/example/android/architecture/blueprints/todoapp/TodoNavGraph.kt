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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.android.architecture.blueprints.todoapp.screens.taskdetail.TaskDetailScreen
import com.example.android.architecture.blueprints.todoapp.screens.tasklist.TaskScreen
import com.example.android.architecture.blueprints.todoapp.utils.AppModalDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TodoNavGraph(
    navController: NavHostController = rememberNavController(),
    navActions: TodoNavigationActions = remember(navController) {
        TodoNavigationActions(navController)
    },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = TodoDestinations.TASKS_ROUTE,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    Surface(color = MaterialTheme.colors.background) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(
                route = TodoDestinations.TASKS_ROUTE
            ) {
                AppModalDrawer(drawerState, currentRoute, navActions) {
                    TaskScreen(
                        onTaskClick = { task -> navActions.navigateToTaskDetail(task.id) },
                        onAddTask = {},
                        openDrawer = { coroutineScope.launch { drawerState.open() } }
                    )
                }
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
