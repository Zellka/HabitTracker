package com.example.habit.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.habit.data.api.ApiHelper
import com.example.habit.data.api.ApiService
import com.example.habit.data.db.DatabaseHelper
import com.example.habit.data.model.*
import com.example.habit.data.repository.HabitRepository
import com.example.habit.utils.Resource
import kotlinx.coroutines.*

class HabitsViewModel(
    private val repository: HabitRepository
) : ViewModel() {

    val habitsList = MutableLiveData<List<Habit>>()
    private val filterList = MutableLiveData<List<Habit>>()
    val errorMessage = MutableLiveData<String>()
    private var job: Job? = null

    fun getHabitsFromApi() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getHabits()
            if (response.body()?.isNotEmpty() == true) {
                repository.deleteHabitsFromDB()
                repository.insertHabitsToDB(response.body()!!)
            }
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    habitsList.postValue(response.body())
                    filterList.postValue(response.body())
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun getHabitsFromDB() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getHabitsFromDB()
            withContext(Dispatchers.Main) {
                if (response.isNotEmpty()) {
                    habitsList.postValue(response)
                    filterList.postValue(response)
                } else {
                    onError("Нет данных")
                }
            }
        }
    }

    fun getDoneToast(times: Int, habitType: Int): String {
        return if (habitType == Type.GOOD.value) {
            when (times) {
                0 -> DoneToast.GOOD_ENOUGH.value
                1 -> DoneToast.GOOD_ENOUGH.value
                else -> DoneToast.GOOD_NO_ENOUGH.value
            }
        } else {
            when (times) {
                0 -> DoneToast.BAD_ENOUGH.value
                1 -> DoneToast.BAD_ENOUGH.value
                else -> DoneToast.BAD_NO_ENOUGH.value
            }
        }
    }

    fun setFilterHabitList(filter: String) {
        habitsList.value = filterList.value?.filter { it.title == filter }
    }

    fun setSortHabitList(sort: Sort) {
        when (sort) {
            Sort.DATE -> {
                habitsList.value = filterList.value?.sortedBy { it.date }
            }
            Sort.NAME -> {
                habitsList.value = filterList.value?.sortedBy { it.title }
            }
            Sort.PRIORITY -> {
                habitsList.value = filterList.value?.sortedBy { it.priority }
            }
        }
    }

    fun setNoFilter() {
        habitsList.value = filterList.value
    }

    private fun onError(message: String) {
        errorMessage.value = message
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

class ViewModelFactory(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitsViewModel::class.java)) {
            return HabitsViewModel(HabitRepository(apiHelper, dbHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}