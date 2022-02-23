package com.example.domain.usecase

import com.example.domain.models.Habit
import com.example.domain.repository.HabitsRepository
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class DeleteHabitUseCaseTest {

    private val testRepository = mock<HabitsRepository>()

    @Test
    fun deleteHabitTest() = runBlocking {
        val habit = Habit(
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
        Mockito.`when`(testRepository.deleteHabit(habit = habit)).thenReturn(Unit)
        val testDispatcher = TestCoroutineDispatcher()
        val useCase = DeleteHabitUseCase(repository = testRepository, dispatcher = testDispatcher)
        val result = useCase.execute(habit = habit)
        Assertions.assertEquals(result, Unit)
    }
}