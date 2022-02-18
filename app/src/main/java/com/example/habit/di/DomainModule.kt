package com.example.habit.di

import com.example.domain.repository.HabitsRepository
import com.example.domain.usecase.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class DomainModule {

    @Provides
    fun provideAddEditHabitUseCase(habitsRepository: HabitsRepository): AddEditHabitUseCase {
        return AddEditHabitUseCase(habitsRepository, Dispatchers.IO)
    }

    @Provides
    fun provideDeleteHabitUseCase(habitsRepository: HabitsRepository): DeleteHabitUseCase {
        return DeleteHabitUseCase(habitsRepository, Dispatchers.IO)
    }

    @Provides
    fun provideDoneHabitUseCase(habitsRepository: HabitsRepository): DoneHabitUseCase {
        return DoneHabitUseCase(habitsRepository, Dispatchers.IO)
    }

    @Provides
    fun provideGetHabitsUseCase(habitsRepository: HabitsRepository): GetHabitsUseCase {
        return GetHabitsUseCase(habitsRepository, Dispatchers.IO)
    }

    @Provides
    fun provideLoadHabitsUseCase(habitsRepository: HabitsRepository): LoadHabitsUseCase {
        return LoadHabitsUseCase(habitsRepository, Dispatchers.IO)
    }

    @Provides
    fun provideToastDoneHabitUseCase(): ToastDoneHabitUseCase {
        return ToastDoneHabitUseCase()
    }
}