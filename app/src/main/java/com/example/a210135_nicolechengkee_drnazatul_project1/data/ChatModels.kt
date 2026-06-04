package com.example.a210135_nicolechengkee_drnazatul_project1.data

data class ChatMessage(
    val text: String,
    val isFromUser: Boolean,
    val timestamp: String
)

data class RecruiterChat(
    val name: String,
    val company: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int = 0,
    val jobTitle: String
)
