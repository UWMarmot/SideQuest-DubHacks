package com.example.sidequest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState


val redsqr = LatLng(47.6559563, -122.3093808)
/*
@Composable
fun MapScreen() {

    var selectedEvent by remember { mutableStateOf<EventPost?>(null) }
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(redsqr, 15f)
    }

    // Display the Google Map in the Compose UI
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        eventsList.forEach { event ->
            if (event.lat != null && event.long != null) {
                val thisPlace = LatLng(event.lat!!.toDouble(), event.long!!.toDouble())
                Marker(
                    state = rememberMarkerState(position = thisPlace),
                    title = event.title,
                    /*
                    onClick = {
                        selectedEvent = event
                        true
                    }

                     */

                )


            }

        }
    }
}


        selectedEvent?.let { event ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = event.title ?: "Event Details")
                    Text(text = "Date: ${event.date}")
                    Text(text = "Planned Attendees: ${event.pop}")

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                selectedEvent = null // Close the card
                            }
                        ) {
                            Text("Close")
                        }

                        Button(
                            onClick = {
                                //navController.navigate("eventScreen") // Navigate to event details screen
                                selectedEvent = null // Close the card
                            }
                        ) {
                            Text("View Event")
                        }
                    }
                }
            }
        }
    }
}

*/

@Composable
fun MapScreen() {

    var selectedEvent by remember { mutableStateOf<EventPost?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(redsqr, 15f)
    }

    // Use Box to overlay the Card on the Google Map
    Box(Modifier.fillMaxSize()) {
        // Display the Google Map in the Compose UI
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            // Add a marker for each event in the eventsList
            eventsList.forEach { event ->
                if (event.lat != null && event.long != null) {
                    val thisPlace = LatLng(event.lat!!.toDouble(), event.long!!.toDouble())
                    Marker(
                        state = rememberMarkerState(position = thisPlace),
                        title = event.title,
                        onClick = {
                            selectedEvent = event // Set the selected event when marker is clicked
                            false // Return false to allow default behavior (show info window)
                        }
                    )
                }
            }
        }

        // Show a card when a marker is clicked (if selectedEvent is not null)
        selectedEvent?.let { event ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter), // Align the card at the bottom of the screen
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = event.title ?: "Event Details")
                    Text(text = "Date: ${event.date}")
                    Text(text = "Planned Attendees: ${event.pop}")

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                selectedEvent = null // Close the card
                            }
                        ) {
                            Text("Close")
                        }

                        Button(
                            onClick = {
                                // navController.navigate("eventScreen") // Uncomment this for navigation
                                selectedEvent = null // Close the card
                            }
                        ) {
                            Text("View Event")
                        }
                    }
                }
            }
        }
    }
}


