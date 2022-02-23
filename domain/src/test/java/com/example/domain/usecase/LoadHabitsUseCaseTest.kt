package com.example.domain.usecase

import com.example.domain.models.Habit
import com.example.domain.repository.HabitsRepository
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class LoadHabitsUseCaseTest {

    private val testRepository = mock<HabitsRepository>()

    @Test
    fun loadHabitsTest() = runBlocking {
        val habits: List<Habit> = listOf(
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
        Mockito.`when`(testRepository.getHabitsApi()).thenReturn(habits)
        Mockito.`when`(testRepository.insertHabitsToDb(habits)).thenReturn(Unit)
        val testDispatcher = TestCoroutineDispatcher()
        val useCase = LoadHabitsUseCase(repository = testRepository, dispatcher = testDispatcher)
        val result = useCase.execute()
        Assertions.assertEquals(result, Unit)
    }
}