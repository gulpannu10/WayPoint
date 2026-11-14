package com.example.data.model

data class ChatMessage(
    val id: String,
    val isFromUser: Boolean,
    val text: String,
    val timestamp: String,
    val visualPreviewUrl: String? = null,
    val visualPreviewLabel: String? = null,
    val badge: String? = null,
    val doctorContext: String? = null,
    val audioDurationSeconds: Int? = null,
    val wayfindingLocation: String? = null,
    val labTrackerSync: String? = null
)

data class SuggestedPrompt(
    val id: String,
    val textEn: String,
    val textHi: String,
    val icon: String
)

data class NurseNavigator(
    val name: String = "Sister Lakshmi",
    val title: String = "LEAD OPD CARE COORDINATOR",
    val supervisingDoctor: String = "Dr. Ananya Sen, MD DM Cardiology",
    val photoUrl: String = "https://lh3.googleusercontent.com/aida-public/AB6AXuASk7c1PxcV45mEbS1dSnMnimn-Mvg6jGTMo7JlqS8-qNAcKPXwzV7J0BE6kCUKn_MxP6sIN8kvXzrgSUE8hN_MJ5XfPR4ao2wCMX_nPhfhn8xz4v5nGHUxFR4QMvhe3OQ1_u56F0magtkv-99UKHE9hgx2O1lexulrJHaknyE0RiN7DtKuqUj7lmKtUUliYPuuJnaaQ5EW8YEctIoOzKf9J1BnzBxR-axxdDL5Jwa4HgQz488L2fZ5uw",
    val isOnline: Boolean = true
)
