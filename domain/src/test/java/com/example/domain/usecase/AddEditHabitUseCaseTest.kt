package com.example.domain.usecase

import com.example.domain.models.HabitPut
import com.example.domain.repository.HabitsRepository
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class AddEditHabitUseCaseTest {

    private val testRepository = mock<HabitsRepository>()

    @Test
    fun addHabitTest() = runBlocking {
        val habitPut = HabitPut(
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
        Mockito.`when`(testRepository.putHabit(habitPut)).thenReturn(Unit)
        val testDispatcher = TestCoroutineDispatcher()
        val useCase = AddEditHabitUseCase(repository = testRepository, dispatcher = testDispatcher)
        val result = useCase.execute(habit = habitPut)
        assertEquals(result, Unit)
    }
}