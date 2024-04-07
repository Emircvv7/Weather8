package com.example.weather2

import android.app.Application
import androidx.room.Room
import com.example.weather2.data.room.AppDatabase

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
