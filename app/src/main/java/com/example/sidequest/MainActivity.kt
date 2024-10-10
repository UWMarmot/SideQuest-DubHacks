package com.example.sidequest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.sidequest.ui.theme.SideQuestTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


data class NavRowComponent (
    val title: String,
    val highlighted: ImageVector,
    val normal: ImageVector,
)

data class EventPost (
    val name: String
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            SideQuestTheme {
                NavBarLoad()

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


@Preview
@Composable
fun GreetingPreview() {
    SideQuestTheme {
        NavBarLoad()
    }
}



