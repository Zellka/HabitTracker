package com.example.habit.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson

class ConverterDates {
    @TypeConverter
    fun datesToJson(value: Array<Long>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToDates(value: String) =
        Gson().fromJson(value, Array<Long>::class.java)
}