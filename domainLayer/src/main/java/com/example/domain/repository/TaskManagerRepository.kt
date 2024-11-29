package com.example.domain.repository

import com.example.domain.model.TaskModel
import com.example.domain.result.Result
import kotlinx.coroutines.flow.Flow

interface TaskManagerRepository {

    suspend fun getTask(id: Int): Result<TaskModel>
    suspend fun insertTask(taskModel: TaskModel): Result<TaskModel>
    suspend fun getAllTasks(): Flow<Result<List<TaskModel>>>
    suspend fun getTaskByName(taskName: String): Result<TaskModel>
    suspend fun updateTask(taskModel: TaskModel): Result<TaskModel>
    suspend fun deleteTask(task: TaskModel): Result<TaskModel>

}