package com.example.habit.data.api

import com.example.habit.data.model.Habit
import com.example.habit.data.model.HabitDone
import com.example.habit.data.model.HabitUID

class ApiHelper(private val apiService: ApiService) {

    suspend fun getHabits() = apiService.getHabits()
    suspend fun putHabit(habit: Habit) = apiService.putHabit(habit)
    suspend fun deleteHabit(habit: HabitUID) = apiService.deleteHabit(habit)
    suspend fun postHabitDone(habit: HabitDone) = apiService.postHabitDone(habit)
}