package com.example.habit.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.habit.data.models.HabitDataBase

@Database(entities = [HabitDataBase::class], version = 1)
@TypeConverters(ConverterDates::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao
}