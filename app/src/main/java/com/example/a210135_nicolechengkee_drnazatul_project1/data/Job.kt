package com.example.a210135_nicolechengkee_drnazatul_project1.data

data class Job(
    val id: Int,
    val title: String,
    val company: String,
    val location: String,
    val salary: String,
    val type: String = "Full Time",
    val description: String = "Great opportunity to grow your career in a fast-paced environment."
)

data class UserProfile(
    val name: String = "Nicole Cheng",
    val email: String = "nicole@gmail.com",
    val skills: List<String> = listOf("HTML", "CSS", "JavaScript","UI/UX"),
    val appliedJobs: List<Int> = emptyList()
)