package com.example.domain.usecase

import com.example.domain.models.HabitPut
import com.example.domain.repository.HabitsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AddEditHabitUseCase(
    private val repository: HabitsRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun execute(habit: HabitPut) {
        withContext(dispatcher) {
            repository.putHabit(habit)
        }
    }
}