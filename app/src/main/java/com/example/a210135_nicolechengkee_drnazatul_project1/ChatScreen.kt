package com.example.a210135_nicolechengkee_drnazatul_project1
import com.example.a210135_nicolechengkee_drnazatul_project1.data.*

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun ChatScreen(navController: NavController, viewModel: JobViewModel) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val activeChatState: MutableState<RecruiterChat?> = remember { mutableStateOf(null) }
    val activeChat: RecruiterChat? = activeChatState.value

    val recruiters = remember {
        listOf(
            RecruiterChat(
                name = "Sarah Lim",
                company = "Hatricks Tech",
                lastMessage = "Hi! We reviewed your profile and would love to schedule an interview.",
                time = "10:30 AM",
                unreadCount = 2,
                jobTitle = "UI/UX Designer"
            ),
            RecruiterChat(
                name = "Daniel Wong",
                company = "Two95 International",
                lastMessage = "Thanks for applying! Could you share your portfolio?",
                time = "Yesterday",
                unreadCount = 1,
                jobTitle = "Android Developer"
            ),
            RecruiterChat(
                name = "Amirah Zain",
                company = "Astra Academy",
                lastMessage = "We have a part-time slot available this weekend. Are you free?",
                time = "Mon",
                unreadCount = 0,
                jobTitle = "Math Tutor"
            ),
            RecruiterChat(
                name = "HR Team",
                company = "D2D Mavericks",
                lastMessage = "Your application is under review. We will get back to you shortly.",
                time = "Sun",
                unreadCount = 0,
                jobTitle = "Financial Maverick"
            )
        )
    }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

        if (activeChat == null) {
            Column(
                Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text(
                    "Messages",
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "${recruiters.sumOf { it.unreadCount }} unread messages",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(16.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(recruiters) { recruiter ->
                        RecruiterRow(recruiter) { activeChatState.value = recruiter }
                    }
                }
            }
        } else {
            ChatConversation(
                recruiter = activeChat,
                onBack = { activeChatState.value = null }
            )
        }

        BottomNav(currentRoute, navController)
    }
}

@Composable
fun RecruiterRow(recruiter: RecruiterChat, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    recruiter.name.first().toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(recruiter.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(recruiter.time, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text(
                    recruiter.company + " • " + recruiter.jobTitle,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                )
                Spacer(Modifier.height(2.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        recruiter.lastMessage,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                    if (recruiter.unreadCount > 0) {
                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .size(20.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                recruiter.unreadCount.toString(),
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatConversation(recruiter: RecruiterChat, onBack: () -> Unit) {

    val inputTextState: MutableState<String> = remember { mutableStateOf("") }
    val inputText: String = inputTextState.value

    val listState = rememberLazyListState()

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                "Hi ${recruiter.name.split(" ").first()}! I'm interested in the ${recruiter.jobTitle} role.",
                isFromUser = true,
                timestamp = "10:00 AM"
            ),
            ChatMessage(
                recruiter.lastMessage,
                isFromUser = false,
                timestamp = recruiter.time
            )
        )
    }

    // Auto-scroll to bottom when new message added
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)
    }

    Column(Modifier.fillMaxSize()) {

        // ── Chat Header ──
        Card(
            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.clickable { onBack() }
                )
                Spacer(Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        recruiter.name.first().toString(),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        recruiter.name,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        recruiter.company,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                }
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        // ── Messages ──
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { msg ->
                MessageBubble(msg)
            }
        }

        // ── Input Row ──
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputTextState.value = it },
                placeholder = { Text("Type a message…") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                singleLine = true
            )
            Spacer(Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (inputText.isNotBlank()) {
                        messages.add(
                            ChatMessage(
                                text = inputText.trim(),
                                isFromUser = true,
                                timestamp = "Now"
                            )
                        )
                        inputTextState.value = ""
                        // Auto-reply simulation
                        messages.add(
                            ChatMessage(
                                text = "Thanks for your message! We'll get back to you soon.",
                                isFromUser = false,
                                timestamp = "Now"
                            )
                        )
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(
                    if (message.isFromUser) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (message.isFromUser) 16.dp else 4.dp,
                        bottomEnd = if (message.isFromUser) 4.dp else 16.dp
                    )
                )
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                message.text,
                color = if (message.isFromUser) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )
        }
        Spacer(Modifier.height(2.dp))
        Text(
            message.timestamp,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}
