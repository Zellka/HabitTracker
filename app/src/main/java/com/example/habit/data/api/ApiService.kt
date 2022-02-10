package com.example.habit.data.api

import com.example.habit.data.model.Habit
import com.example.habit.data.model.HabitDone
import com.example.habit.data.model.HabitUID
import retrofit2.http.*

interface ApiService {

    @GET("habit")
    suspend fun getHabits(@Header("Authorization") token: String = "token"): List<Habit>

    @PUT("habit")
    suspend fun putHabit(
        @Body habit: Habit,
        @Header("Authorization") token: String = "token"
    ): HabitUID

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(
        @Body habit: HabitUID,
        @Header("Authorization") token: String = "token"
    )

    @POST("habit_done")
    suspend fun postHabitDone(
        @Body habit: HabitDone,
        @Header("Authorization") token: String = "token"
    )
}