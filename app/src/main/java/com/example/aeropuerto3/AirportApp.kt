package com.example.aeropuerto3

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aeropuerto3.data.Airport
import com.example.aeropuerto3.model.Favorite


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirportApp(
    modifier: Modifier = Modifier,
    viewModel: AirportAppViewModel = viewModel(factory = AirportAppViewModel.factory)
){
    val record by viewModel.searchResult.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val hasSelected by viewModel.hasSelected.collectAsState()

    val airports by viewModel.getAirportsSatisfied(record).collectAsState(emptyList())
    val favoritos by viewModel.getFavorites().collectAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Flight Search") }
            )
        },
        content = { padding ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(padding),
            ) {
                Busqueda(viewModel = viewModel, modifier = modifier, record = record)
                Spacer(modifier = Modifier.width(16.dp))
                if(isSearching){
                    if(!hasSelected){
                        ListOfAirports(airports = airports,viewModel = viewModel )
                    } else {
                        ListOfVuelos(viewModel = viewModel)
                    }

                }else{
                    Favoritos(viewModel = viewModel, modifier = modifier, listaFavoritos = favoritos)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Busqueda(
    viewModel: AirportAppViewModel,
    modifier: Modifier,
    record:String
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = record,
            onValueChange = {
                viewModel.updateSearchResult(it)
                viewModel.updateIsSearching(it.isNotEmpty())
                viewModel.updatehasSelected(false)

            },
            placeholder = {
                Text(text = stringResource(R.string.busqueda))
            },
            singleLine = true,
        )
    }
}

@Composable
fun Favoritos(
    viewModel: AirportAppViewModel,
    modifier: Modifier,
    listaFavoritos:List<Favorite>
){
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)){
        Text(text = "Favorite Routes")

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(8.dp),
        ) {

            items(listaFavoritos){
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Text(
                            text = it.departureCode,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(20.dp))

                        Text(text = it.destinationCode)
                    }
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ListOfVuelos(viewModel: AirportAppViewModel){
    val airport = viewModel.airportSelected.value
    viewModel.processFlightList(airport)
    var cod = viewModel.uiState.value.code
    var destinationList = viewModel.uiState.value.destinationList
    var favList = viewModel.uiState.value.favoriteList
    Column (
        modifier = Modifier.padding(8.dp)
    ){
        Text(
            text = "Fligths from "+ cod,
            fontWeight = FontWeight.Bold
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(8.dp),
        ) {
            items(destinationList, key = { it.id }){item ->
                ResultadosVuelos(
                    airport = airport,
                    destinationAirportCode = item.code,
                    destinationAirportName = item.name,
                )
            }
        }
    }

}

@Composable
fun ResultadosVuelos(
    airport: Airport,
    destinationAirportCode: String,
    destinationAirportName: String,
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row (){
            Column(
                modifier = Modifier
                    .weight(10f)
                    .padding(25.dp)
            ) {

                Text(
                    text = "DEPART",
                    fontSize = 13.sp,
                    color = androidx.compose.ui.graphics.Color.Black
                )
                Text(
                    text = airport.code+" "+ airport.name,
                )
                Text(
                    text = "ARRIVAL",
                    fontSize = 13.sp,
                    color = androidx.compose.ui.graphics.Color.Black
                )
                Text(
                    text = destinationAirportCode
                )
                Text(
                    text = destinationAirportName
                )
            }

            IconButton(
                onClick = {
                    //Implementar favoritos
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    tint = androidx.compose.ui.graphics.Color.Black,
                    contentDescription = null
                )
            }
        }
    }
}
@Composable
fun ListOfAirports(
    airports: List<Airport>,
    viewModel: AirportAppViewModel
){
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.padding(8.dp),
    ) {

        items(airports){item: Airport ->
            FlightCard(airport = item, viewModel =viewModel)
        }
    }
}

@Composable
fun FlightCard(
    airport: Airport,
    modifier: Modifier = Modifier,
    viewModel: AirportAppViewModel
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                viewModel.updatehasSelected(value = true)
                viewModel.updateAirportSelected(value = airport)
            }

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = airport.code,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(20.dp))

            Text(text = airport.name)
        }
    }
}


