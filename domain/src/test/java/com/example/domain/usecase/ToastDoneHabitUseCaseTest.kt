package com.example.domain.usecase

import com.example.domain.models.Habit
import com.example.domain.models.ToastHabit
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ToastDoneHabitUseCaseTest {

    @Test
    fun toastDoneHabitTest() {
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
        val useCase = ToastDoneHabitUseCase()
        val result = useCase.execute(habit)
        Assertions.assertEquals(result, ToastHabit.GOOD_NO_ENOUGH.value)
    }
}