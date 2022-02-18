package com.example.domain.usecase

import com.example.domain.models.Habit
import com.example.domain.repository.HabitsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DeleteHabitUseCase(private val repository: HabitsRepository,
                         private val dispatcher: CoroutineDispatcher
) {
    suspend fun execute(habit: Habit) {
        withContext(dispatcher) {
            repository.deleteHabit(habit)
        }
    }
}