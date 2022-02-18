package com.example.domain.usecase

import com.example.domain.models.Habit
import com.example.domain.repository.HabitsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetHabitsUseCase(private val repository: HabitsRepository,
                       private val dispatcher: CoroutineDispatcher
) {
    fun execute(): Flow<List<Habit>> {
       return repository.getHabitsDb().flowOn(dispatcher)
    }
}