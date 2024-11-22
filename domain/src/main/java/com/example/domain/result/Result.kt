package com.example.domain.result

import com.example.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

sealed class Result<out T> {
    data class Success <T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    data object Loading: Result<Nothing>()
}