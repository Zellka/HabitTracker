package com.example.domain.usecase

import com.example.domain.models.Habit
import com.example.domain.repository.HabitsRepository
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class GetHabitsUseCaseTest {

    private val testRepository = mock<HabitsRepository>()

    @Test
    fun getHabitsTest() = runBlocking {
        val habits: Flow<List<Habit>> = flowOf()
        listOf(
            Habit(
                color = 126,
                count = 7,
                date = 7,
                description = "Описание",
                done_dates = arrayOf(),
                frequency = 7,
                priority = 0,
                title = "Название",
                type = 0,
                uid = "uid1"
            )
        )
        val testDispatcher = TestCoroutineDispatcher()
        val actual = habits.flowOn(testDispatcher)
        Mockito.`when`(testRepository.getHabitsDb()).thenReturn(actual)
        val useCase = GetHabitsUseCase(repository = testRepository, dispatcher = testDispatcher)
        val result = useCase.execute()
        Assertions.assertEquals(result, actual)
    }
}