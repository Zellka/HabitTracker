package com.example.habit.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.AddEditHabitUseCase
import com.example.habit.presentation.viewmodels.AddHabitViewModel

class AddHabitViewModelFactory(val addEditHabitUseCase: AddEditHabitUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddHabitViewModel(
            addEditHabitUseCase
        ) as T
    }
}