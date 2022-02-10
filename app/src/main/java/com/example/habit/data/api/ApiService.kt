package com.example.habit.data.api

import com.example.habit.data.model.Habit
import com.example.habit.data.model.HabitDone
import com.example.habit.data.model.HabitUID
import retrofit2.http.*

interface ApiService {

    @GET("habit")
    suspend fun getHabits(): List<Habit>

    @PUT("habit")
    suspend fun putHabit(@Body habit: Habit): HabitUID

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(@Body habit: HabitUID)

    @POST("habit_done")
    suspend fun postHabitDone(@Body habit: HabitDone)
}