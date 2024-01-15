package com.example.aeropuerto3

import android.app.Application
import com.example.aeropuerto3.data.AirportDatabase
import com.example.aeropuerto3.data.AppContainer

class AirportApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer.AppDataContainer(this)
    }

    val database: AirportDatabase by lazy { AirportDatabase.getDatabase(this) }
}