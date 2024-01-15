package com.example.aeropuerto3.data

import android.content.Context

interface AppContainer {
    val airportRepository: AirportRepository

    class AppDataContainer(private val context: Context): AppContainer{
        override val airportRepository: AirportRepository by lazy {
            DefaultAirportRepository(AirportDatabase.getDatabase(context).airportDao())
        }
    }
}
