package com.example.habit.presentation.viewmodels

import androidx.lifecycle.*
import com.example.domain.models.Habit
import com.example.domain.models.HabitDone
import com.example.domain.usecase.*
import com.example.habit.presentation.Sort
import kotlinx.coroutines.launch
import java.util.*

class HabitViewModel(
    private val getHabitsUseCase: GetHabitsUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val loadHabitsUseCase: LoadHabitsUseCase,
    private val doneHabitUseCase: DoneHabitUseCase,
    private val toastDoneHabitUseCase: ToastDoneHabitUseCase
) : ViewModel() {

    private val mutableIsError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = mutableIsError
    private val mutableIsLoad: MutableLiveData<Boolean> = MutableLiveData()
    val isLoad: LiveData<Boolean> = mutableIsLoad

    private val mutableFilterText: MutableLiveData<String> = MutableLiveData()
    val filterText: LiveData<String> = mutableFilterText
    private val mutableSort: MutableLiveData<Sort> = MutableLiveData()
    val sortText: LiveData<Sort> = mutableSort

    private val mutableToastDone: MutableLiveData<String> = MutableLiveData()
    val toastDone: LiveData<String> = mutableToastDone

    private fun loadHabits() {
        mutableIsLoad.value = true
        mutableIsError.value = false
        viewModelScope.launch {
            try {
                loadHabitsUseCase.execute()
                mutableIsLoad.value = false
            } catch (e: Exception) {
                mutableIsLoad.value = false
                mutableIsError.value = true
            }
        }
    }


    fun getHabits(): LiveData<List<Habit>> {
        loadHabits()
        return getHabitsUseCase.execute().asLiveData()
    }

    fun deleteHabit(habit: Habit) {
        mutableIsError.value = false
        viewModelScope.launch {
            try {
                deleteHabitUseCase.execute(habit)
            } catch (e: Exception) {
                mutableIsError.value = true
            }
        }
    }

    fun doneHabit(habit: Habit) {
        mutableIsError.value = false
        viewModelScope.launch {
            try {
                doneHabitUseCase.execute(HabitDone((Date().time / 1000).toInt(), habit.uid))
                mutableToastDone.value = toastDoneHabitUseCase.execute(habit)
            } catch (e: Exception) {
                mutableIsError.value = true
            }
        }
    }

    fun filter(input: String) {
        mutableFilterText.value = input
    }

    fun sort(input: Sort) {
        mutableSort.value = input
    }
}