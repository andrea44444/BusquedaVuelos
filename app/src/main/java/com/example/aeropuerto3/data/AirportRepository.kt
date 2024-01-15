package com.example.aeropuerto3.data

import com.example.aeropuerto3.model.Favorite
import kotlinx.coroutines.flow.Flow

interface AirportRepository {
    fun getAllFavorites():Flow<List<Favorite>>
    fun getAllAirports(): Flow<List<Airport>>
}