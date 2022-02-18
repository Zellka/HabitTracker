package com.example.domain.models

import java.util.*
import java.io.Serializable

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
    val uid: String
) : Serializable {
    fun getDoneTime(): Int {
        val day = 60 * 60 * 24
        val now = (Date().time / 1000).toInt()
        val span = now - day * frequency
        val times = count - done_dates.filter { it >= span }.size
        return times.coerceAtLeast(0)
    }
}

data class HabitPut(
    val color: Int?,
    val count: Int,
    val date: Long,
    val description: String,
    val done_dates: Array<Long>,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String?
)

enum class Type(val value: Int) {
    GOOD(0),
    BAD(1)
}