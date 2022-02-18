package com.example.domain.usecase

import com.example.domain.models.HabitDone
import com.example.domain.repository.HabitsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DoneHabitUseCase(private val repository: HabitsRepository,
                       private val dispatcher: CoroutineDispatcher
) {
    suspend fun execute(habit: HabitDone) {
        withContext(dispatcher) {
            repository.doneHabit(habit)
        }
    }
}