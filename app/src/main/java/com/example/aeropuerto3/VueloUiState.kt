package com.example.aeropuerto3

import com.example.aeropuerto3.data.Airport
import com.example.aeropuerto3.model.Favorite

data class VueloUiState (
    val code: String = "",
    val favoriteList: List<Favorite> = emptyList(),
    val destinationList: List<Airport> = emptyList(),
    val departureAirport: Airport = Airport(),
)