package com.example.domain.usecase

import com.example.domain.models.Habit
import com.example.domain.models.ToastHabit
import com.example.domain.models.Type

class ToastDoneHabitUseCase {
    fun execute(habit: Habit): String {
        val times = habit.getDoneTime()
        return if (habit.type == Type.GOOD.value) {
            when (times) {
                0 -> ToastHabit.GOOD_ENOUGH.value
                1 -> ToastHabit.GOOD_ENOUGH.value
                else -> ToastHabit.GOOD_NO_ENOUGH.value
            }
        } else {
            when (times) {
                0 -> ToastHabit.BAD_ENOUGH.value
                1 -> ToastHabit.BAD_ENOUGH.value
                else -> ToastHabit.BAD_NO_ENOUGH.value
            }
        }
    }
}