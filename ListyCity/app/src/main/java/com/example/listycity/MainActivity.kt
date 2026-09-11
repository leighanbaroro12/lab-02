package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = {cityRepository.addCity(it)},
                        onRemoveCity = {cityRepository.removeCity(it)},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CityListScreen(
    cities: List<String>,
    onAddCity: (String) -> Unit,
    onRemoveCity: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember {mutableStateOf("")}
    var showAddCity: Boolean by remember {mutableStateOf(false)}
    var currentCityClicked: String by remember  {mutableStateOf("")}

    Column(modifier = modifier.fillMaxSize()) {

        Text(
            "ListyCity",
            fontSize = 30.sp,
            color = Color(7, 33, 26),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
        )

        Row(modifier = Modifier.padding(all = 16.dp)) {
            Button(
                onClick = { showAddCity = !showAddCity },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(74, 163, 136)),
                modifier = Modifier
                    .padding(8.dp)
                    .width(170.dp)
                    .height(75.dp)
            ) {
                Text(
                    text = "ADD CITY",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = {
                    showAddCity = false
                    if (currentCityClicked.isNotBlank()) {
                        onRemoveCity(currentCityClicked)
                        currentCityClicked = ""
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(74, 163, 136)),
                modifier = Modifier
                    .padding(8.dp)
                    .width(170.dp)
                    .height(75.dp)
            ) {
                Text(
                    text = "DELETE CITY",
                    fontSize = 18.sp
                )
            }
        }

        if (showAddCity) {
            Row(modifier = Modifier.padding(all = 16.dp)) {

                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City name") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (newCityName.isNotBlank()) {
                            onAddCity(newCityName)
                            newCityName = ""
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(74, 163, 136)),
                    modifier = Modifier
                        .padding(10.dp)
                        .width(120.dp)
                ) {
                    Text( "CONFIRM")
                }
            }
        }

        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(cities) { city ->
                CityRow(
                    city = city,
                    cityClicked = {cityClicked ->  currentCityClicked = cityClicked}
                )
            }
        }
    }
}

@Composable
fun CityRow(
    city: String,
    cityClicked: (String) -> Unit
) {
    var cityBoxColor: Color = Color(30, 106, 84)

    Button(
        onClick = {cityClicked(city)},
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = cityBoxColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp)
    ) {
        Text(
        text = city,
        fontSize = 30.sp,
        )
    }
}

class CityRepository {
    private val _cities = mutableStateListOf(
        "Edmonton", "Vancouver", "Moscow",
        "Sydney", "Berlin", "Vienna",
        "Tokyo", "Beijing", "Osaka",
        "New Delhi"
    )

    val cities: List<String>
        get() = _cities

    fun addCity(city: String) {
        _cities.add(city)
    }

    // For lab-02 participation exercise
    fun removeCity(city: String) {
        _cities.remove(city)
    }
}
