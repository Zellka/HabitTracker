package com.example.habit.di

import android.content.Context
import com.example.domain.usecase.*
import com.example.habit.presentation.factory.AddHabitViewModelFactory
import com.example.habit.presentation.factory.HabitViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideHabitViewModelFactory(
        getHabitsUseCase: GetHabitsUseCase,
        deleteHabitUseCase: DeleteHabitUseCase,
        loadHabitsUseCase: LoadHabitsUseCase,
        doneHabitUseCase: DoneHabitUseCase,
        toastDoneHabitUseCase: ToastDoneHabitUseCase
    ): HabitViewModelFactory {
        return HabitViewModelFactory(
            getHabitsUseCase,
            deleteHabitUseCase,
            loadHabitsUseCase,
            doneHabitUseCase,
            toastDoneHabitUseCase
        )
    }

    @Provides
    fun provideAddHabitViewModelFactory(addEditHabitUseCase: AddEditHabitUseCase): AddHabitViewModelFactory {
        return AddHabitViewModelFactory(
            addEditHabitUseCase
        )
    }
}