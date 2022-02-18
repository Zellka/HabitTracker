package com.example.habit.app

import android.app.Application
import com.example.habit.di.AppComponent
import com.example.habit.di.AppModule
import com.example.habit.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}