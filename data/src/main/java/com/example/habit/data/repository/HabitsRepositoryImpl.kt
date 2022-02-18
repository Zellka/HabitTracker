package com.example.habit.data.repository

import com.example.domain.models.Habit
import com.example.domain.models.HabitDone
import com.example.domain.models.HabitPut
import com.example.domain.models.HabitUID
import com.example.domain.repository.HabitsRepository
import com.example.habit.data.models.HabitDataBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitsRepositoryImpl(
    private val api: com.example.habit.data.api.ApiService,
    private val dao: com.example.habit.data.db.HabitDao
) :
    HabitsRepository {

    override fun getHabitsDb(): Flow<List<Habit>> {
        return dao.getHabits().map { list -> list.map { it.toHabit() } }
    }

    override suspend fun getHabitsApi(): List<Habit> {
        return api.getHabits()
    }

    override suspend fun putHabit(habit: HabitPut) {
        api.putHabit(habit)
    }

    override suspend fun deleteHabit(habit: Habit) {
        api.deleteHabit(HabitUID(habit.uid))
        dao.deleteHabit(HabitDataBase(habit))
    }

    override suspend fun doneHabit(habit: HabitDone) {
        api.postHabitDone(habit)
    }

    override suspend fun insertHabitsToDb(habits: List<Habit>) {
        dao.insertHabits(habits.map { HabitDataBase(it) })
    }

    override suspend fun clearHabitsFromDb() {
        dao.clearHabits()
    }
}