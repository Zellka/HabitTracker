package com.example.habit.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class Habit(
    val color: Int?,
    val count: Int,
    val date: Long,
    val description: String,
    val done_dates: Array<Long>,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    @PrimaryKey val uid: String
) : Parcelable {
    fun getDoneTime(): Int {
        val day = 60 * 60 * 24
        val now = (Date().time / 1000).toInt()
        val span = now - day * frequency
        val times = count - done_dates.filter { it >= span }.size
        return times.coerceAtLeast(0)
    }
}

@Parcelize
data class HabitPut(
    val color: Int?,
    val count: Int,
    val date: Int,
    val description: String,
    val done_dates: Array<Long>,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String?
) : Parcelable