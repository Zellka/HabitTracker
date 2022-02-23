package com.example.habit.presentation.view

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.screen.Screen
import com.example.habit.R

class AllHabitsScreen : Screen<AllHabitsScreen>() {
    val buttonAddHabit = KView {
        withId(R.id.btn_add_habit)
    }
}