package com.example.weather2.app

import android.app.Application
import androidx.room.Room
import com.example.weather2.data.dao.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var weatherDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        weatherDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "weather-db").allowMainThreadQueries()
            .build()
    }
}
