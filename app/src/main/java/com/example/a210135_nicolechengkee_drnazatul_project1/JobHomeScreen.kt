package com.example.a210135_nicolechengkee_drnazatul_project1
import com.example.a210135_nicolechengkee_drnazatul_project1.data.*

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun JobHomeScreen(navController: NavController, viewModel: JobViewModel) {

    var locationInput by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf("") }

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(16.dp),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    Column {
                        Text("Good Morning,", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            "Find Your Dream Job",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary)
                            .clickable { navController.navigate(Screen.Profile.route) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, null, tint = MaterialTheme.colorScheme.onSecondary)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            CustomTextField(locationInput) { locationInput = it }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    resultMessage = if (locationInput.isNotBlank())
                        "Searching jobs in $locationInput"
                    else "Please enter a location"
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Find Jobs") }

            if (resultMessage.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(resultMessage, Modifier.padding(12.dp), color = MaterialTheme.colorScheme.onSecondary)
                }
            }

            Spacer(Modifier.height(20.dp))

            Text("Featured Jobs", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

            Row(Modifier.horizontalScroll(rememberScrollState())) {
                viewModel.featuredJobs.forEach { job ->
                    FeaturedJobCard(job.title, job.company, job.salary) {
                        navController.navigate(Screen.Detail.createRoute(job.id))
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Text("Recommended Jobs", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

            viewModel.recommendedJobs.forEach { job ->
                JobCard(
                    title = job.title,
                    company = job.company,
                    location = job.location,
                    salary = job.salary,
                    isRecommended = true,
                    onClick = { navController.navigate(Screen.Detail.createRoute(job.id)) }
                )
            }
        }

        BottomNav(currentRoute, navController)
    }
}