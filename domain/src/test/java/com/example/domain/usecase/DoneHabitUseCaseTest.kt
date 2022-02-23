package com.example.domain.usecase

import com.example.domain.models.HabitDone
import com.example.domain.repository.HabitsRepository
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class DoneHabitUseCaseTest {

    private val testRepository = mock<HabitsRepository>()

    @Test
    fun doneHabitTest() = runBlocking {
        val habitDone = HabitDone(
            date = 7,
            habit_uid = "uid1"
        )
        Mockito.`when`(testRepository.doneHabit(habit = habitDone)).thenReturn(Unit)
        val testDispatcher = TestCoroutineDispatcher()
        val useCase = DoneHabitUseCase(repository = testRepository, dispatcher = testDispatcher)
        val result = useCase.execute(habit = habitDone)
        Assertions.assertEquals(result, Unit)
    }
}