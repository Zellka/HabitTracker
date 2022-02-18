package com.example.habit.data.api

import com.example.domain.models.Habit
import com.example.domain.models.HabitDone
import com.example.domain.models.HabitPut
import com.example.domain.models.HabitUID
import retrofit2.http.*

interface ApiService {

    @GET("habit")
    suspend fun getHabits(): List<Habit>

    @PUT("habit")
    suspend fun putHabit(@Body habit: HabitPut): HabitUID

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(@Body habit: HabitUID)

    @POST("habit_done")
    suspend fun postHabitDone(@Body habit: HabitDone)
}