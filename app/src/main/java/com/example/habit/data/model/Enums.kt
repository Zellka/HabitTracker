package com.example.habit.data.model

enum class Sort(val value: Boolean) {
    DATE(false),
    PRIORITY(false),
    NAME(false)
}

enum class Priority(val value: Int) {
    LOW(0),
    MEDIUM(1),
    HIGH(2)
}

enum class Type(val value: Int) {
    GOOD(0),
    BAD(1)
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}