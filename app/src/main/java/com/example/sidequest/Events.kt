package com.example.sidequest

import android.location.Location
import android.service.autofill.OnClickAction
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.*



fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371

    val dLat = degToRad(lat2 - lat1)
    val dLon = degToRad(lon2 - lon1)

    val a = sin(dLat / 2) * sin(dLat / 2) + cos(degToRad(lat1)) * cos(degToRad(lat2)) * sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))


    val distance = R * c
    return distance
}

fun degToRad(deg: Double): Double {
    return deg * PI / 180.0
}

@Composable
fun ExploreScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(eventsList) { post ->
            EventPostCard(post)
        }
    }
}

@Composable
fun EventPostCard(eventPost: EventPost) {
    var isFullScreen by remember { mutableStateOf(false) }

    var commentText by remember { mutableStateOf("") }

    var comments by remember { mutableStateOf(mutableListOf<String>()) }

    var distance = eventPost.lat?.let { eventPost.long?.let { it1 ->
        haversineDistance(it, it1, 47.6559563, -122.309380)
    } }

    if (distance != null) {
        distance = (round(distance * 100) / 100)
    }


    Card(

        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { isFullScreen = !isFullScreen },  //Hopefully functional full-screen toggele

    ) {
        if (isFullScreen) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = eventPost.title ?: "No title",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Planned Attendees: " + eventPost.pop,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Date and Time: " + eventPost.date,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "This event is only $distance kilometers away!",
                    style = MaterialTheme.typography.bodyLarge
                )

                // Event description in full-screen
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = eventPost.desc ?: "No description available",
                    style = MaterialTheme.typography.bodyMedium
                )

                // Comment section
                Spacer(modifier = Modifier.height(16.dp))
                Text("Comments", style = MaterialTheme.typography.titleMedium)
                comments.forEach { comment ->
                    Text(
                        text = comment,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(4.dp)
                    )
                }

                // Add comment input
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    label = { Text("Add a comment") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Button to submit comment
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (commentText.isNotBlank()) {
                            comments.add(commentText)
                            commentText = "" // Clear input after submitting
                        }
                    }
                ) {
                    Text("Submit Comment")
                }
            }
        } else {
            // Regular card layout
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = eventPost.title ?: "No title",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Planned Attendees: " + eventPost.pop,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Date and Time: " + eventPost.date,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "This event is only $distance kilometers away!",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


