package com.example.habit.data.db

import com.example.habit.data.model.Habit

interface DatabaseHelper {
    suspend fun getHabits(): List<Habit>

    suspend fun insertHabits(habits: List<Habit>)

    suspend fun clearHabits()
}

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun getHabits(): List<Habit> = appDatabase.habitDao().getHabits()

    override suspend fun insertHabits(habits: List<Habit>) =
        appDatabase.habitDao().insertHabits(habits)

    override suspend fun clearHabits() = appDatabase.habitDao().clearHabits()
}