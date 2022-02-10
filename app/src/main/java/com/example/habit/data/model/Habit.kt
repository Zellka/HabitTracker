package com.example.habit.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Habit(
    val color: Long?,
    val count: Int,
    val date: Long,
    val description: String,
    val done_dates: Array<Long>,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String?,
):Parcelable

enum class Priority(val value: Int) {
    LOW(0),
    MEDIUM(1),
    HIGH(2)
}

enum class Type(val value: Int) {
    GOOD(0),
    BAD(1)
}