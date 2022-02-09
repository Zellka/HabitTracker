package com.example.habit.data.model

data class Habit(
    val color: Long,
    val count: Int,
    val date: Int,
    val description: String,
    val done_dates: Array<Int>,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String,
)

enum class Priority(val value: Int) {
    LOW(0),
    MEDIUM(1),
    HIGH(2)
}

enum class Type(val value: Int) {
    GOOD(0),
    BAD(1)
}