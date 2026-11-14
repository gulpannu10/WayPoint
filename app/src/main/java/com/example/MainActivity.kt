package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.data.model.NurseNavigator
import com.example.data.repository.WaypointRepository
import com.example.ui.components.*
import com.example.ui.screens.CareAiScreen
import com.example.ui.screens.JourneyScreen
import com.example.ui.screens.RecordsScreen
import com.example.ui.screens.VisitGuideScreen
import com.example.ui.theme.SereneBackground
import com.example.ui.theme.WaypointTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val repository = remember { WaypointRepository() }
            WaypointApp(repository = repository)
        }
    }
}

@Composable
fun WaypointApp(repository: WaypointRepository) {
    val isHindi by repository.isHindiMode.collectAsState()
    val currentTab by repository.currentTab.collectAsState()
    val patientProfile by repository.patientProfile.collectAsState()
    val timelineSteps by repository.timelineSteps.collectAsState()
    val prepItems by repository.prepItems.collectAsState()
    val attendantLogs by repository.attendantLogs.collectAsState()
    val amenities by repository.amenities.collectAsState()
    val records by repository.diagnosticRecords.collectAsState()
    val chatMessages by repository.chatMessages.collectAsState()
    val suggestedPrompts by repository.suggestedPrompts.collectAsState()
    val isWalletSaved by repository.isWalletPassSaved.collectAsState()
    val isEscortDispatched by repository.isEscortDispatched.collectAsState()
    val playingAudioId by repository.playingAudioMessageId.collectAsState()

    val showEmergencyDialog by repository.showEmergencyDialog.collectAsState()
    val showSahayakDialog by repository.showSahayakDialog.collectAsState()
    val showAddAttendantDialog by repository.showAddAttendantDialog.collectAsState()
    val showNurseCallDialog by repository.showNurseCallDialog.collectAsState()

    val nurse = remember { NurseNavigator() }

    // If user presses system back and is on another tab, return to Journey
    if (currentTab != 0) {
        BackHandler {
            repository.selectTab(0)
        }
    }

    val tabTitle = when (currentTab) {
        0 -> if (isHindi) "ओपीडी यात्रा" else "Patient Journey"
        1 -> if (isHindi) "जांच रिकॉर्ड" else "Clinical Records"
        2 -> if (isHindi) "केयर AI गाइड" else "Care AI Guide"
        3 -> if (isHindi) "विज़िट नेविगेशन" else "Visit Guide"
        else -> ""
    }

    WaypointTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = SereneBackground,
            topBar = {
                WaypointTopBar(
                    profile = patientProfile,
                    isHindi = isHindi,
                    currentTabTitle = tabTitle,
                    onToggleLanguage = { repository.toggleLanguage() },
                    onEmergencyClick = { repository.openEmergencyDialog() }
                )
            },
            bottomBar = {
                WaypointBottomNav(
                    selectedTab = currentTab,
                    isHindi = isHindi,
                    onTabSelected = { repository.selectTab(it) }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Crossfade(targetState = currentTab, label = "tab_crossfade") { tab ->
                    when (tab) {
                        0 -> JourneyScreen(
                            timelineSteps = timelineSteps,
                            prepItems = prepItems,
                            isHindi = isHindi,
                            attendantName = patientProfile.attendantName,
                            onNavigateToRoute = { repository.selectTab(3) },
                            onViewRecords = { repository.selectTab(1) },
                            onTogglePrepItem = { repository.togglePrepItem(it) },
                            onRequestSahayak = { repository.openSahayakDialog() }
                        )
                        1 -> RecordsScreen(
                            records = records,
                            isHindi = isHindi,
                            abhaId = patientProfile.abhaId
                        )
                        2 -> CareAiScreen(
                            chatMessages = chatMessages,
                            suggestedPrompts = suggestedPrompts,
                            nurse = nurse,
                            isHindi = isHindi,
                            playingAudioId = playingAudioId,
                            onToggleAudio = { repository.toggleAudioPlayback(it) },
                            onSendMessage = { repository.sendMessage(it) },
                            onTalkToNurse = { repository.openNurseCallDialog() },
                            onNavigateToHydration = { repository.selectTab(3) }
                        )
                        3 -> VisitGuideScreen(
                            profile = patientProfile,
                            attendantLogs = attendantLogs,
                            amenities = amenities,
                            isHindi = isHindi,
                            isWalletSaved = isWalletSaved,
                            isEscortDispatched = isEscortDispatched,
                            onToggleWallet = { repository.toggleWalletPass() },
                            onRequestEscort = { repository.requestEscort() },
                            onAddAttendant = { repository.openAddAttendantDialog() },
                            onUrgentHelp = { repository.openEmergencyDialog() }
                        )
                    }
                }
            }

            // Dialogs
            if (showEmergencyDialog) {
                EmergencyHelpDialog(
                    isHindi = isHindi,
                    onDismiss = { repository.closeEmergencyDialog() },
                    onAlertFloorStaff = { repository.requestEscort() }
                )
            }

            if (showSahayakDialog) {
                SahayakRequestDialog(
                    isHindi = isHindi,
                    onDismiss = { repository.closeSahayakDialog() },
                    onConfirm = { repository.requestSahayak() }
                )
            }

            if (showAddAttendantDialog) {
                AddAttendantDialog(
                    isHindi = isHindi,
                    onDismiss = { repository.closeAddAttendantDialog() },
                    onAdd = { name, phone, relation ->
                        repository.addAttendant(name, phone, relation)
                    }
                )
            }

            if (showNurseCallDialog) {
                NurseCallDialog(
                    nurse = nurse,
                    isHindi = isHindi,
                    onDismiss = { repository.closeNurseCallDialog() }
                )
            }
        }
    }
}
