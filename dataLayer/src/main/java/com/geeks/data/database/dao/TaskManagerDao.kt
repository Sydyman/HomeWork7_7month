package com.geeks.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Update
import com.example.domain.result.Result
import com.geeks.data.dto.TaskDto
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskManagerDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertTask(taskDto: TaskDto)

    @Query("SELECT * FROM taskdto")
    fun getAllTasks(): Flow<List<TaskDto>>
    
    @Query("SELECT * FROM taskdto WHERE taskName = :taskName LIMIT 1")
    suspend fun getTaskByName(taskName: String): TaskDto

    @Query("SELECT * FROM TaskDto WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskDto

    @Update
    suspend fun updateTask(taskDto: TaskDto)

    @Delete
    suspend fun deleteTask(taskDto: TaskDto)
}