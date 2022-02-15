package com.example.habit.data.repository

import com.example.habit.data.api.ApiHelper
import com.example.habit.data.db.DatabaseHelper
import com.example.habit.data.model.Habit
import com.example.habit.data.model.HabitDone
import com.example.habit.data.model.HabitPut
import com.example.habit.data.model.HabitUID

class HabitRepository(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper) {

    suspend fun getHabits() = apiHelper.getHabits()
    suspend fun putHabit(habit: HabitPut) = apiHelper.putHabit(habit)
    suspend fun deleteHabit(habit: HabitUID) = apiHelper.deleteHabit(habit)
    suspend fun postHabitDone(habit: HabitDone) = apiHelper.postHabitDone(habit)

    suspend fun getHabitsFromDB() = dbHelper.getHabits()
    suspend fun insertHabitsToDB(habits: List<Habit>) = dbHelper.insertHabits(habits)
    suspend fun deleteHabitsFromDB() = dbHelper.clearHabits()
}