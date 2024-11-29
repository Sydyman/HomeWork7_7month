package com.geeks.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geeks.data.database.dao.TaskManagerDao
import com.geeks.data.dto.TaskDto

@Database(entities = [TaskDto::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskManagerDao(): TaskManagerDao

}