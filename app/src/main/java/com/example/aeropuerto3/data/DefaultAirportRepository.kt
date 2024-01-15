package com.example.aeropuerto3.data

import com.example.aeropuerto3.model.Favorite
import kotlinx.coroutines.flow.Flow

class DefaultAirportRepository(private val airportDao: AirportDao): AirportRepository {
    override fun getAllAirports(): Flow<List<Airport>>  = airportDao.getAirports()
    override fun getAllFavorites(): Flow<List<Favorite>> = airportDao.getAllFavoritesFlow()

}