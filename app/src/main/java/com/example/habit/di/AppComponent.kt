package com.example.habit.di

import com.example.habit.presentation.view.AddHabitFragment
import com.example.habit.presentation.view.FilterFragment
import com.example.habit.presentation.view.HabitsFragment
import dagger.Component

@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
interface AppComponent {
    fun inject(habitsFragment: HabitsFragment)

    fun inject(addHabitFragment: AddHabitFragment)

    fun inject(filterFragment: FilterFragment)
}