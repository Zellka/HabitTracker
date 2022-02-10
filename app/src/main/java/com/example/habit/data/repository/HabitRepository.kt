package com.example.habit.data.repository

import com.example.habit.data.api.ApiHelper
import com.example.habit.data.model.Habit
import com.example.habit.data.model.HabitDone
import com.example.habit.data.model.HabitUID

class HabitRepository(private val apiHelper: ApiHelper) {

    suspend fun getHabits() = apiHelper.getHabits()
    suspend fun putHabit(habit: Habit) = apiHelper.putHabit(habit)
    suspend fun deleteHabit(habit: HabitUID) = apiHelper.deleteHabit(habit)
    suspend fun postHabitDone(habit: HabitDone) = apiHelper.postHabitDone(habit)
}