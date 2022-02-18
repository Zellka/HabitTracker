package com.example.habit.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.HabitPut
import com.example.domain.usecase.AddEditHabitUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddHabitViewModel(
    private val addEditHabitUseCase: AddEditHabitUseCase
) : ViewModel() {

    private val mutableIsError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = mutableIsError

    fun addEditHabit(habit: HabitPut) {
        mutableIsError.value = false
        viewModelScope.launch {
            try {
                addEditHabitUseCase.execute(habit)
            } catch (e: Exception) {
                mutableIsError.value = true
            }
        }
    }
}