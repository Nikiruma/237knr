package com.example.gpsapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {

    private val LOCATION_PERMISSION_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var locationText by remember {
                mutableStateOf("Няма локация")
            }

            Surface(
                modifier = Modifier.fillMaxSize()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = locationText,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))


                    Button(
                        onClick = {

                            if (
                                ActivityCompat.checkSelfPermission(
                                    this@MainActivity,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {

                                ActivityCompat.requestPermissions(
                                    this@MainActivity,
                                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                    LOCATION_PERMISSION_CODE
                                )

                            } else {

                                val fusedLocationClient =
                                    LocationServices
                                        .getFusedLocationProviderClient(
                                            this@MainActivity
                                        )

                                fusedLocationClient.lastLocation
                                    .addOnSuccessListener { location ->

                                        if (location != null) {

                                            locationText =
                                                "Latitude: ${location.latitude}\n" +
                                                        "Longitude: ${location.longitude}"

                                        } else {
                                            locationText =
                                                "Не може да се вземе локация"
                                        }
                                    }
                            }
                        }
                    ) {

                        Text("Вземи локация")
                    }
                }
            }
        }
    }
}