package com.geeks.cleanArch.presentation.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geeks.cleanArch.presentation.model.TaskUI
import com.geeks.cleanArch.data.dto.TaskDto
import com.geeks.cleanArch.domain.usecase.GetAllTasksUseCase
import com.geeks.cleanArch.domain.usecase.InsertTaskUseCase
import com.geeks.cleanArch.presentation.model.toDomain
import com.geeks.cleanArch.presentation.model.toUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val insertTaskUseCase: InsertTaskUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase
) : ViewModel() {

    private val _tasks = MutableLiveData<List<TaskUI>>()
    val tasks: LiveData<List<TaskUI>> get() = _tasks

    private val _insertMessage = MutableLiveData<String>()
    val insertMessage: LiveData<String> get() = _insertMessage

    fun insertTask(taskUI: TaskUI) {
        viewModelScope.launch(Dispatchers.IO) {
            val message = insertTaskUseCase.insertTask(taskUI.toDomain())
            _insertMessage.postValue(message)
        }
    }

    fun loadTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val allTasks = getAllTasksUseCase()
            _tasks.postValue(allTasks.map { it.toUI() })
        }
    }
}