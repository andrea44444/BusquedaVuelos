package com.example.aeropuerto3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.aeropuerto3.data.Airport
import com.example.aeropuerto3.data.AirportDao
import com.example.aeropuerto3.data.AirportRepository
import com.example.aeropuerto3.model.Favorite
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AirportAppViewModel(
    private val airportDao: AirportDao,
    val airportRepository: AirportRepository

): ViewModel(){

    private val _searchResult = MutableStateFlow("")
    val searchResult: StateFlow<String> = _searchResult

    private val _airportSelected = MutableStateFlow(Airport())
    val airportSelected: MutableStateFlow<Airport> = _airportSelected

    private var _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    private var _hasSelected = MutableStateFlow(false)
    val hasSelected: StateFlow<Boolean> = _hasSelected

    private val _uiState = MutableStateFlow(VueloUiState())
    val uiState: StateFlow<VueloUiState> = _uiState

    fun updateIsSearching(value: Boolean) {
        _isSearching.value = value
    }
    fun updatehasSelected(value: Boolean) {
        _hasSelected.value = value
    }
    fun updateSearchResult(search: String){
        _searchResult.value = search
    }
    fun updateAirportSelected(value: Airport){
        _airportSelected.value = value
    }


    fun processFlightList(airport: Airport) {
        viewModelScope.launch {
            val ff = airportRepository.getAllFavorites()
            val aa = airportRepository.getAllAirports()

            ff.collect { favoriteList ->
                aa.collect { airportList ->
                    _uiState.update {
                        uiState.value.copy(
                            code = airport.code,
                            favoriteList = favoriteList,
                            destinationList = airportList,
                            departureAirport = airport
                        )
                    }
                }
            }
        }
    }

    fun getAirportsSatisfied(query: String): Flow<List<Airport>> = airportDao.getAirportsSatisfied(query)
    fun getFavorites():Flow<List<Favorite>> = airportDao.getAllFavoritesFlow()

    companion object{
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AirportApplication
                AirportAppViewModel(application.database.airportDao(),application.container.airportRepository)
            }
        }
    }
}