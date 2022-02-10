package com.example.habit.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.habit.data.api.ApiHelper
import com.example.habit.data.api.ApiService
import com.example.habit.data.model.HabitDone
import com.example.habit.data.model.HabitUID
import com.example.habit.data.repository.HabitRepository
import com.example.habit.utils.Resource
import kotlinx.coroutines.Dispatchers

class HabitsViewModel(
    private val repository: HabitRepository
) : ViewModel() {

    fun getHabits() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getHabits()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error"))
        }
    }

    fun deleteHabit(habit: HabitUID) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.deleteHabit(habit)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error"))
        }
    }

    fun postHabitDone(habit: HabitDone) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.postHabitDone(habit)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error"))
        }
    }
}

class ViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitsViewModel::class.java)) {
            return HabitsViewModel(HabitRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}