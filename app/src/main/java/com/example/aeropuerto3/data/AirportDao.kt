package com.example.aeropuerto3.data

import androidx.room.Dao
import androidx.room.Query
import com.example.aeropuerto3.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("SELECT * from airport WHERE iata_code= :query OR name LIKE '%' || :query || '%' ")
    fun getAirportsSatisfied(query: String): Flow<List<Airport>>

    @Query("SELECT * from airport")
    fun getAirports(): Flow<List<Airport>>

    @Query("Select * from favorite ORDER BY id ASC")
    fun getAllFavoritesFlow(): Flow<List<Favorite>>
}