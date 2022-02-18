package com.example.domain.usecase

import com.example.domain.repository.HabitsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoadHabitsUseCase(
    private val repository: HabitsRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun execute() {
        withContext(dispatcher) {
            val habits = repository.getHabitsApi()
            repository.insertHabitsToDb(habits)
        }
    }
}