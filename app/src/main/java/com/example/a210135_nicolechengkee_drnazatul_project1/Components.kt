package com.example.a210135_nicolechengkee_drnazatul_project1
import com.example.a210135_nicolechengkee_drnazatul_project1.data.*

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun FeaturedJobCard(title: String, company: String, salary: String, onClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        Modifier
            .width(200.dp)
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(20.dp))
            .clickable {
                expanded = !expanded
                if (!expanded) onClick()
            }
            .animateContentSize()
            .padding(16.dp)
    ) {
        Text(title, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
        Text(company, color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f))

        if (expanded) {
            Spacer(Modifier.height(8.dp))
            Text("Salary: $salary", color = MaterialTheme.colorScheme.onPrimary)
            Spacer(Modifier.height(8.dp))
            TextButton(
                onClick = onClick,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary)
            ) { Text("View Details →") }
        }
    }
}

// ─── Recommended Job Card ───

@Composable
fun JobCard(
    title: String,
    company: String,
    location: String,
    salary: String,
    isRecommended: Boolean = false,
    onClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isRecommended) Color(0xFFF4F0E4)
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(Modifier.padding(16.dp).animateContentSize()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Work, null)
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text(title, fontWeight = FontWeight.Bold)
                    Text(company, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(location, fontSize = 12.sp)
                }
                Icon(Icons.Outlined.FavoriteBorder, null)
            }

            Spacer(Modifier.height(10.dp))

            Row {
                TagChip("Remote")
                TagChip(salary)
            }

            if (expanded) {
                Spacer(Modifier.height(10.dp))
                Text(
                    "Great opportunity to grow your career.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(10.dp))
                TextButton(onClick = onClick) { Text("View Full Details →") }
            }
        }
    }
}

// ─── Tag Chips ───

@Composable
fun TagChip(text: String) {
    Box(
        Modifier
            .padding(end = 6.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, fontSize = 12.sp)
    }
}

@Composable
fun TagChipLight(text: String) {
    Box(
        Modifier
            .padding(end = 6.dp)
            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimary)
    }
}

// ─── Info Card (used in Detail screen) ───

@Composable
fun InfoCard(label: String, value: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))
            Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}


@Composable
fun FormSection(label: String, content: @Composable () -> Unit) {
    Column(Modifier.padding(bottom = 16.dp)) {
        Text(
            label,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        content()
    }
}

// ─── Bottom Navigation Bar ───

@Composable
fun BottomNav(currentRoute: String?, navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp),
        Arrangement.SpaceAround
    ) {
        BottomNavItem(
            icon = Icons.Default.Home,
            label = "Home",
            selected = currentRoute == Screen.Home.route
        ) {
            navController.navigate(Screen.Home.route) { launchSingleTop = true }
        }
        BottomNavItem(
            icon = Icons.Default.Work,
            label = "Jobs",
            selected = currentRoute == Screen.Jobs.route
        ) {
            navController.navigate(Screen.Jobs.route) { launchSingleTop = true }
        }
        BottomNavItem(
            icon = Icons.Default.Email,
            label = "Chat",
            selected = currentRoute == Screen.Chat.route
        ) {
            navController.navigate(Screen.Chat.route) { launchSingleTop = true }
        }
        BottomNavItem(
            icon = Icons.Default.Person,
            label = "Profile",
            selected = currentRoute == Screen.Profile.route
        ) {
            navController.navigate(Screen.Profile.route) { launchSingleTop = true }
        }
    }
}

@Composable
fun BottomNavItem(icon: ImageVector, label: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Icon(
            icon, null,
            tint = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            label,
            color = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp
        )
    }
}

// ─── Custom Text Field ───

@Composable
fun CustomTextField(value: String, onChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onChange,
        placeholder = { Text("City or remote") },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}
