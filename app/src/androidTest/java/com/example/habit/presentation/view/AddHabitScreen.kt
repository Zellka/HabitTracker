package com.example.habit.presentation.view

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.example.habit.R

class AddHabitScreen : Screen<AddHabitScreen>() {
    val editTextName = KEditText {
        withId(R.id.edit_name)
    }
    val editTextDescription = KEditText {
        withId(R.id.edit_description)
    }
    val radioGoodType = KView {
        withId(R.id.type_good)
    }
    val editTextCount = KEditText {
        withId(R.id.edit_count)
    }
    val editTextFrequency = KEditText {
        withId(R.id.edit_frequency)
    }
    val buttonOk = KButton {
        withId(R.id.btn_ok)
    }
}