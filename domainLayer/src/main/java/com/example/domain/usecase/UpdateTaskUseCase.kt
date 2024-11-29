package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository
import com.example.domain.result.Result

class UpdateTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {

    suspend fun updateTask(taskModel: TaskModel): Result<TaskModel> {
        try {
            taskManagerRepository.updateTask(taskModel)
            return Result.Success(taskModel)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error updating task")
        }
        return Result.Success(taskModel)
    }
}