package com.example.data.model

data class PatientProfile(
    val name: String = "Priya Sharma",
    val uhid: String = "APL-94021",
    val abhaId: String = "91-8472-1049-2810",
    val opdToken: String = "A-42",
    val hospitalPavilion: String = "Apollo Pavilion",
    val ageGender: String = "58/F",
    val avatarUrl: String = "https://lh3.googleusercontent.com/aida-public/AB6AXuDTsNoR6nt5R8f2cfhM5XLpBbqXikBD07pvqc5yQJl6Mur-iTESJjR53MrNq0krkhRVarochUi14r1m75ZdeZZlrXAoeV0QM-M0p0P36ZjEkBOz9P8T5WT4nt17UQVPdIyT9Q-OddtN3K3dX_UqhqEAKvEs5WdDZewtYyDrOCYb6gOaMuycJhOaJPJ1mooAwZBTzO39LpADxk7r07206y-g5Qse4huyH9Qri7fzuNCDsyTmN0usXayR_Q",
    val attendantName: String = "Rajesh Sharma",
    val attendantRole: String = "Spouse / तीमारदार",
    val attendantPhone: String = "+91 98201 44024"
)

enum class StepStatus {
    COMPLETED,
    IN_PROGRESS,
    UPCOMING
}

data class TimelineStep(
    val id: Int,
    val stepNumber: Int,
    val titleEn: String,
    val titleHi: String,
    val room: String,
    val status: StepStatus,
    val timeLabel: String,
    val staffLead: String,
    val detailNoteEn: String,
    val detailNoteHi: String,
    val vitalsBadges: List<String> = emptyList()
)

data class PreparationItem(
    val id: String,
    val textEn: String,
    val textHi: String,
    val isCompleted: Boolean,
    val isCrucial: Boolean = false
)

data class AttendantLog(
    val id: String,
    val type: String, // "WhatsApp" or "SMS"
    val message: String,
    val timestamp: String
)

data class Amenity(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val locationEn: String,
    val locationHi: String,
    val iconName: String
)

data class DiagnosticRecord(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val department: String,
    val time: String,
    val statusText: String,
    val isCompleted: Boolean,
    val summary: String,
    val doctorVerified: String? = null,
    val metrics: List<Pair<String, String>> = emptyList()
)
