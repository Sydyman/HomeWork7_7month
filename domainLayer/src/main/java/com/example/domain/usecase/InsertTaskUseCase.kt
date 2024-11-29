package com.example.domain.usecase

import com.example.domain.model.TaskModel
import com.example.domain.repository.TaskManagerRepository
import com.example.domain.result.Result

class InsertTaskUseCase(private val taskManagerRepository: TaskManagerRepository) {

    suspend fun insertTask(taskModel: TaskModel): Result<TaskModel> {
        return try {
            val existingTask = taskManagerRepository.getTaskByName(taskModel.taskName)
            if (existingTask != null) {
                return Result.Error("Task with the same name already exists.")
            }

            taskManagerRepository.insertTask(taskModel)
            Result.Success(taskModel)
        } catch (e: Exception) {
            Result.Error("Task added successfully")
        }
    }
}

