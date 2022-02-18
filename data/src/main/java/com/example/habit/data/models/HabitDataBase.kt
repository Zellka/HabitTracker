package com.example.habit.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.Habit

@Entity
data class HabitDataBase(
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
) {
    constructor(habit: Habit) : this(
        habit.color,
        habit.count,
        habit.date,
        habit.description,
        habit.done_dates,
        habit.frequency,
        habit.priority,
        habit.title,
        habit.type,
        habit.uid
    )

    fun toHabit(): Habit {
        return Habit(
            color,
            count,
            date,
            description,
            done_dates,
            frequency,
            priority,
            title,
            type,
            uid
        )
    }
}