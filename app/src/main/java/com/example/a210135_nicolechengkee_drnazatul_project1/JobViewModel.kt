package com.example.a210135_nicolechengkee_drnazatul_project1
import com.example.a210135_nicolechengkee_drnazatul_project1.data.*

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class JobViewModel : ViewModel() {

    val featuredJobs = listOf(
        Job(1, "UI/UX Designer", "Hatricks Tech", "KL • Remote", "RM 3,500", "Remote"),
        Job(2, "Android Developer", "Two95 International", "Selangor • Hybrid", "RM 4,800", "Hybrid"),
        Job(3, "Marketing Intern", "Seeka Technology", "KL • Full Time", "RM 1,200", "Full Time")
    )

    val recommendedJobs = listOf(
        Job(4, "Math Tutor", "Astra Academy", "PJ • Part Time", "RM 25/hr", "Part Time"),
        Job(5, "Real Estate Agent", "ESP Properties", "Cheras • Part Time", "RM 15/hr", "Part Time"),
        Job(6, "Financial Maverick", "D2D Mavericks", "Remote", "RM 3,000", "Remote"),
        Job(7, "Video Editor", "Rent Wheels", "KL • Contract", "RM 3,500", "Contract")
    )

    val allJobs get() = featuredJobs + recommendedJobs

    var userProfile by mutableStateOf(UserProfile())
        private set

    fun getJobById(id: Int): Job? = allJobs.find { it.id == id }

    fun applyToJob(jobId: Int) {
        if (jobId !in userProfile.appliedJobs) {
            userProfile = userProfile.copy(appliedJobs = userProfile.appliedJobs + jobId)
        }
    }

    fun hasApplied(jobId: Int) = jobId in userProfile.appliedJobs
}