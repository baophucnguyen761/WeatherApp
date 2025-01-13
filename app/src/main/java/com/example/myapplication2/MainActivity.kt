package com.example.myapplication2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication2.ui.theme.BlueJC
import com.example.myapplication2.ui.theme.DarkBlueJC


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherScreen()
        }
    }

    @Composable
    fun WeatherScreen(){
        val viewModel: WeatherViewModel = viewModel()
        val weatherData by viewModel.weatherData.collectAsState()
        var city by remember{
            mutableStateOf("")
        }

        val apiKey="10b427d0dde03804b6eb665fcaaf924e"
        Box(modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.ba),
                contentScale = ContentScale.FillBounds
            )){
            Column(modifier =  Modifier.fillMaxSize()
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top

            ) {
                // Add "Weather" Image
                Spacer(modifier = Modifier.height(100.dp))
                Image(
                    painter = painterResource(id = R.drawable.weatherlogo_processed), // Replace with your "Weather" text image
                    contentDescription = "Weather Text",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = {
                        Text(
                            text = "City",
                            color = Color.Black, // Use a color that contrasts with the background
                            fontSize = 16.sp // Increase font size for better readability
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White, // Transparent for better integration
                        unfocusedContainerColor = Color.White,
                        unfocusedIndicatorColor = BlueJC,
                        focusedIndicatorColor = BlueJC,
                        focusedLabelColor = BlueJC // Use a noticeable color when focused
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = Color.Black, // Ensure the text entered is visible
                        fontSize = 16.sp // Adjust font size for text entry
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.fetchWeather(city, apiKey) },
                    colors = ButtonDefaults.buttonColors(BlueJC)
                ){
                    Text(text = "Check Weather")
                }
                Spacer(modifier = Modifier.height(16.dp))
                weatherData?.let{
                    Row(modifier =  Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        WeatherCard(label = city, value = it.name, icon = Icons.Default.Place)
                        WeatherCard(label = "Temperature", value = "${it.main.temp} °C", icon = Icons.Default.Star)

                    }
                    Row(modifier =  Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        WeatherCard(label = "Humidity", value = "${it.main.humidity}%", icon =Icons.Default.Warning)
                        WeatherCard(label = "Description", value = it.weather[0].description, icon =Icons.Default.Info)

                    }
                }

            }
        }
    }
}

@Composable
fun WeatherCard(label: String, value: String, icon: ImageVector) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(150.dp)
            .border(
                width = 4.dp,
                color = BlueJC,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = DarkBlueJC,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = label, fontSize = 14.sp, color = DarkBlueJC)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = BlueJC
                )
            }
        }
    }
}





