package com.example.habit.data.db

import androidx.room.*
import com.example.habit.data.models.HabitDataBase
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habitdatabase")
    fun getHabits(): Flow<List<HabitDataBase>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabits(habits: List<HabitDataBase>)

    @Query("DELETE FROM habitdatabase")
    fun clearHabits()

    @Insert
    suspend fun addHabit(habit: HabitDataBase)

    @Update
    suspend fun updateHabit(habit: HabitDataBase)

    @Delete
    suspend fun deleteHabit(habit: HabitDataBase)
}