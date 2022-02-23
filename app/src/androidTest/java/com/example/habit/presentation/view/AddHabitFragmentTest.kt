package com.example.habit.presentation.view

import androidx.test.rule.ActivityTestRule
import org.junit.Test
import com.agoda.kakao.screen.Screen.Companion.onScreen
import org.junit.Rule

class AddHabitFragmentTest {

    @Rule
    @JvmField
    var activity: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test() {
        onScreen<AllHabitsScreen> {
            buttonAddHabit.click()
        }

        onScreen<AddHabitScreen> {
            editTextName.replaceText("Пить воду")
            editTextDescription.replaceText("1 литр")
            radioGoodType.click()
            editTextCount.replaceText("7")
            editTextFrequency.replaceText("7")
            buttonOk.click()
        }
    }
}