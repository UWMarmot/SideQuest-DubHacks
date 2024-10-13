package com.example.sidequest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sidequest.ui.theme.SideQuestTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


data class NavRowComponent (
    val title: String,
    val highlighted: ImageVector,
    val normal: ImageVector,
)

data class EventPost(
    var title: String? = "",
    var desc: String? = "",
    var long: Double? = 0.0,
    var lat: Double? = 0.0,
    var date: String? = "",
    var pop: Int? = 0,
)

public lateinit var fusedLocationClient: FusedLocationProviderClient
val eventsList = mutableListOf<EventPost>()



var firebaseDatabase: FirebaseDatabase? = null


var databaseReference: DatabaseReference? = null


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        FirebaseApp.initializeApp(this)
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase?.getReference("events");

        setContent {

            SideQuestTheme {
                NavBarLoad()
                LoadEvents()
                //writeAndReadFromFirebase()

            }
        }
    }
}


@Composable
fun NavBarLoad() {

    //Sets up navController to allow navigation
    val navController = rememberNavController()


    val items = listOf(
        NavRowComponent(
            title = "Explore",
            highlighted = Icons.Filled.Star,
            normal = Icons.Outlined.Star
        ),
        NavRowComponent(
            title = "Map",
            highlighted = Icons.Filled.LocationOn,
            normal = Icons.Outlined.LocationOn
        ),
        NavRowComponent(
            title = "Camera",
            highlighted = Icons.Filled.PlayArrow,
            normal = Icons.Outlined.PlayArrow
        ),
        NavRowComponent(
            title = "Events",
            highlighted = Icons.Filled.DateRange,
            normal = Icons.Outlined.DateRange
        ),
        NavRowComponent(
            title = "Profile",
            highlighted = Icons.Filled.AccountCircle,
            normal = Icons.Outlined.AccountCircle
        )

    )
    //Variable tracks nav button is selected
    var isSelected by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        //Creates bottom nav bar
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = isSelected == index,
                        onClick = { isSelected = index; navController.navigate(item.title) },
                        label = {
                            Text(item.title)
                        },

                        icon = {
                            Icon(
                                imageVector =  item.highlighted,
                                contentDescription = item.title

                            )
                        }

                    )
                }
            }
        }) { innerPadding ->
        //Declares NavHost, and the different accessible routes from the host
        NavHost(
            navController = navController,
            startDestination = "Explore", // Define default route
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = "Explore") {
                ExploreScreen()
            }
            composable(route = "Map") {
                MapScreen()
            }
            composable(route = "Camera") {
                CameraScreen()
            }
            composable(route = "Events") {
                EventsScreen()
            }
            composable(route = "Profile") {
                ProfileScreen()
            }
        }

    }
}

@Composable
fun LoadEvents() {

    databaseReference?.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            println("got there")
            if (snapshot.exists()) {
                println(snapshot)
                // Iterate over the children of the snapshot

                for (eventSnapshot in snapshot.children) {
                    val event = EventPost()
                    event.desc = eventSnapshot.child("Desc").getValue(String::class.java)
                    event.long = eventSnapshot.child("Long").getValue(String::class.java)?.toDoubleOrNull()
                    event.title = eventSnapshot.child("Title").getValue(String::class.java)
                    event.lat = eventSnapshot.child("Lat").getValue(String::class.java)?.toDoubleOrNull()
                    event.pop = eventSnapshot.child("Population").getValue(Long::class.java)?.toInt()
                    event.date = eventSnapshot.child("Date").getValue(String::class.java)
                    eventsList.add(event)


                    
                }

            } else {
                println("Error getting data: No events found.")
            }
        }

        override fun onCancelled(error: DatabaseError) {
            println("Error reading data: ${error.message}")
        }
    })
}


@Preview
@Composable
fun GreetingPreview() {
    SideQuestTheme {
        NavBarLoad()
    }
}



