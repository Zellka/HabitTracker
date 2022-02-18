package com.example.habit.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.*
import com.example.habit.presentation.viewmodels.HabitViewModel

class HabitViewModelFactory(
    val getHabitsUseCase: GetHabitsUseCase,
    val deleteHabitUseCase: DeleteHabitUseCase,
    val loadHabitsUseCase: LoadHabitsUseCase,
    val doneHabitUseCase: DoneHabitUseCase,
    val toastDoneHabitUseCase: ToastDoneHabitUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HabitViewModel(
            getHabitsUseCase,
            deleteHabitUseCase,
            loadHabitsUseCase,
            doneHabitUseCase,
            toastDoneHabitUseCase
        ) as T
    }
}