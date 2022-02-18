package com.example.domain.repository

import com.example.domain.models.Habit
import com.example.domain.models.HabitDone
import com.example.domain.models.HabitPut
import com.example.domain.models.HabitUID
import kotlinx.coroutines.flow.Flow

interface HabitsRepository {

    fun getHabitsDb(): Flow<List<Habit>>

    suspend fun getHabitsApi(): List<Habit>

    suspend fun putHabit(habit: HabitPut)

    suspend fun deleteHabit(habit: Habit)

    suspend fun doneHabit(habit: HabitDone)

    suspend fun insertHabitsToDb(habits: List<Habit>)

    suspend fun clearHabitsFromDb()
}