package com.example.habit.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.habit.data.model.Habit

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    suspend fun getHabits(): List<Habit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabits(habits: List<Habit>)

    @Query("DELETE FROM habit")
    suspend fun clearHabits()
}