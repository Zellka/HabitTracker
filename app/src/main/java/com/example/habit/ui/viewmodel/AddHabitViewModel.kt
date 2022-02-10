package com.example.habit.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.habit.data.api.ApiHelper
import com.example.habit.data.model.Habit
import com.example.habit.data.repository.HabitRepository
import com.example.habit.utils.Resource
import kotlinx.coroutines.Dispatchers

class AddHabitViewModel(
    private val repository: HabitRepository
) : ViewModel() {

    fun putHabit(habit: Habit) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.putHabit(habit)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error"))
        }
    }
}

class AddHabitViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddHabitViewModel::class.java)) {
            return AddHabitViewModel(HabitRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}