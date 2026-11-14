package com.example.data.repository

import com.example.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WaypointRepository {

    private val _isHindiMode = MutableStateFlow(false)
    val isHindiMode: StateFlow<Boolean> = _isHindiMode.asStateFlow()

    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    private val _patientProfile = MutableStateFlow(PatientProfile())
    val patientProfile: StateFlow<PatientProfile> = _patientProfile.asStateFlow()

    private val _isWalletPassSaved = MutableStateFlow(false)
    val isWalletPassSaved: StateFlow<Boolean> = _isWalletPassSaved.asStateFlow()

    private val _isEscortDispatched = MutableStateFlow(false)
    val isEscortDispatched: StateFlow<Boolean> = _isEscortDispatched.asStateFlow()

    private val _isSahayakRequested = MutableStateFlow(false)
    val isSahayakRequested: StateFlow<Boolean> = _isSahayakRequested.asStateFlow()

    private val _showEmergencyDialog = MutableStateFlow(false)
    val showEmergencyDialog: StateFlow<Boolean> = _showEmergencyDialog.asStateFlow()

    private val _showSahayakDialog = MutableStateFlow(false)
    val showSahayakDialog: StateFlow<Boolean> = _showSahayakDialog.asStateFlow()

    private val _showAddAttendantDialog = MutableStateFlow(false)
    val showAddAttendantDialog: StateFlow<Boolean> = _showAddAttendantDialog.asStateFlow()

    private val _showNurseCallDialog = MutableStateFlow(false)
    val showNurseCallDialog: StateFlow<Boolean> = _showNurseCallDialog.asStateFlow()

    private val _playingAudioMessageId = MutableStateFlow<String?>(null)
    val playingAudioMessageId: StateFlow<String?> = _playingAudioMessageId.asStateFlow()

    private val _timelineSteps = MutableStateFlow(
        listOf(
            TimelineStep(
                id = 1,
                stepNumber = 1,
                titleEn = "Vitals & Nurse Triage (Room 102)",
                titleHi = "महत्वपूर्ण लक्षण और नर्स ट्राइएज (कमरा १०२)",
                room = "Room 102",
                status = StepStatus.COMPLETED,
                timeLabel = "9:00 AM",
                staffLead = "Sr. Nurse Kavita • OPD Triage Station",
                detailNoteEn = "Nurse triage completed. Height, weight, temperature, and automated resting vitals recorded.",
                detailNoteHi = "नर्स ट्राइएज पूरा हुआ। महत्वपूर्ण लक्षण दर्ज किए गए।",
                vitalsBadges = listOf("BP: 122/80", "HR: 68 bpm", "SpO2: 99%", "Vitals Logged")
            ),
            TimelineStep(
                id = 2,
                stepNumber = 2,
                titleEn = "Heart Ultrasound / Echo (Room 214)",
                titleHi = "इकोकार्डियोग्राफी / अल्ट्रासाउंड (कमरा २१४)",
                room = "Room 214",
                status = StepStatus.IN_PROGRESS,
                timeLabel = "10:15 AM",
                staffLead = "Technologist Marcus Sterling is ready for Token A-42",
                detailNoteEn = "Room 214 • Est. duration 25 mins. Doppler sound-wave evaluation.",
                detailNoteHi = "कमरा २१४ • अनुमानित समय २५ मिनट • डॉक्टर के लिए रिपोर्ट तैयार होगी।"
            ),
            TimelineStep(
                id = 3,
                stepNumber = 3,
                titleEn = "Consultation with Dr. Ananya Sen",
                titleHi = "डॉ. अनन्या सेन के साथ परामर्श (कमरा २०८)",
                room = "Exam Suite 208",
                status = StepStatus.UPCOMING,
                timeLabel = "~11:15 AM",
                staffLead = "Senior Cardiologist (वरिष्ठ हृदय रोग विशेषज्ञ)",
                detailNoteEn = "Review ultrasound images, blood lipid panel, and finalize prescription.",
                detailNoteHi = "अल्ट्रासाउंड रिपोर्ट और रक्त जांच की समीक्षा और प्रिस्क्रिप्शन।"
            ),
            TimelineStep(
                id = 4,
                stepNumber = 4,
                titleEn = "OPD Pharmacy Counter & Ayushman / TPA Desk",
                titleHi = "ओपीडी फार्मेसी काउंटर और आयुष्मान / टीपीए डेस्क",
                room = "Ground Floor Counter 4",
                status = StepStatus.UPCOMING,
                timeLabel = "~11:45 AM",
                staffLead = "Pharmacist Rajesh G. & TPA Executive",
                detailNoteEn = "Medicine dispensing & cashless insurance clearance.",
                detailNoteHi = "दवा वितरण और कैशलेस क्लीयरेंस और डिस्चार्ज।"
            )
        )
    )
    val timelineSteps: StateFlow<List<TimelineStep>> = _timelineSteps.asStateFlow()

    private val _prepItems = MutableStateFlow(
        listOf(
            PreparationItem(
                id = "prep_1",
                textEn = "Morning medications taken with water (दवा ली गई)",
                textHi = "सुबह की दवा पानी के साथ ली गई",
                isCompleted = true
            ),
            PreparationItem(
                id = "prep_2",
                textEn = "Fasting window verified with nurse desk",
                textHi = "नर्स डेस्क के साथ उपवास सत्यापन पूर्ण",
                isCompleted = true
            ),
            PreparationItem(
                id = "prep_3",
                textEn = "Remove metallic mangalsutra / jewellery before scan",
                textHi = "स्कैन से पहले धातु के गहने या मंगलसूत्र हटा लें",
                isCompleted = false,
                isCrucial = true
            )
        )
    )
    val prepItems: StateFlow<List<PreparationItem>> = _prepItems.asStateFlow()

    private val _attendantLogs = MutableStateFlow(
        listOf(
            AttendantLog(
                id = "log_1",
                type = "WhatsApp",
                message = "WhatsApp: \"Priya Sharma moved to Echo Lab (कमरा २१४)\"",
                timestamp = "Just now"
            ),
            AttendantLog(
                id = "log_2",
                type = "SMS",
                message = "SMS: \"OPD Vitals & ECG recorded successfully at 9:25 AM\"",
                timestamp = "9:25 AM"
            )
        )
    )
    val attendantLogs: StateFlow<List<AttendantLog>> = _attendantLogs.asStateFlow()

    private val _amenities = MutableStateFlow(
        listOf(
            Amenity(
                id = "amenity_1",
                titleEn = "Prasad / Veg Cafeteria & Chai",
                titleHi = "प्रसाद / शुद्ध शाकाहारी कैंटीन व चाय",
                locationEn = "Ground Floor, Near Gate 2",
                locationHi = "ग्राउंड फ्लोर, गेट २ के पास",
                iconName = "local_cafe"
            ),
            Amenity(
                id = "amenity_2",
                titleEn = "TPA / Cashless Insurance",
                titleHi = "टीपीए / कैशलेस बीमा डेस्क",
                locationEn = "Counter 4, Block B Desk",
                locationHi = "काउंटर ४, ब्लॉक बी डेस्क",
                iconName = "health_and_safety"
            ),
            Amenity(
                id = "amenity_3",
                titleEn = "Senior Citizen & Wheelchair",
                titleHi = "वरिष्ठ नागरिक और व्हीलचेयर सहायता",
                locationEn = "Free Attendant Helpdesk 2B",
                locationHi = "मुफ्त सहायक (हेल्पडेस्क २बी)",
                iconName = "elderly"
            ),
            Amenity(
                id = "amenity_4",
                titleEn = "Drinking Water / RO Station",
                titleHi = "शुद्ध शीतल पेयजल (आरओ स्टेशन)",
                locationEn = "Cold Water Dispenser (Floor 2)",
                locationHi = "ठंडा पीने का पानी (मंजिल २)",
                iconName = "water_drop"
            )
        )
    )
    val amenities: StateFlow<List<Amenity>> = _amenities.asStateFlow()

    private val _diagnosticRecords = MutableStateFlow(
        listOf(
            DiagnosticRecord(
                id = "rec_1",
                titleEn = "Routine Blood Lipid & Cardiac Enzymes",
                titleHi = "रूटीन ब्लड लिपिड और कार्डियक एंजाइम",
                department = "Central Clinical Biochemistry Lab",
                time = "9:30 AM (In Lab)",
                statusText = "Processing in laboratory • ~35m left",
                isCompleted = false,
                summary = "Samples drawn by Phlebotomist Anita M. Testing Troponin I, Total Cholesterol, HDL/LDL ratio, HbA1c.",
                metrics = listOf(
                    "Sample ID" to "#L-409",
                    "Status" to "Centrifuge Complete",
                    "Expected" to "11:00 AM"
                )
            ),
            DiagnosticRecord(
                id = "rec_2",
                titleEn = "12-Lead Resting Electrocardiogram (ECG)",
                titleHi = "१२-लीड रेस्टिंग ईसीजी",
                department = "Cardiology Diagnostic Suite",
                time = "9:15 AM",
                statusText = "Completed & Verified ✓",
                isCompleted = true,
                summary = "Normal Sinus Rhythm. Heart rate 68 bpm. PR interval 156 ms, QRS duration 88 ms, QTc 418 ms. No acute ischemic ST changes.",
                doctorVerified = "Dr. Ananya Sen, MD DM",
                metrics = listOf(
                    "Heart Rate" to "68 bpm",
                    "Rhythm" to "Normal Sinus",
                    "PR Interval" to "156 ms",
                    "QTc" to "418 ms"
                )
            ),
            DiagnosticRecord(
                id = "rec_3",
                titleEn = "Baseline Clinical Vitals",
                titleHi = "प्रारंभिक ओपीडी महत्वपूर्ण लक्षण",
                department = "Nursing Triage Station 2B",
                time = "9:00 AM",
                statusText = "Completed ✓",
                isCompleted = true,
                summary = "Blood pressure stable, pulse regular, oxygen saturation optimal at ambient room air.",
                doctorVerified = "Sr. Nurse Kavita",
                metrics = listOf(
                    "Blood Pressure" to "122/80 mmHg",
                    "Pulse" to "68 bpm",
                    "SpO2" to "99%",
                    "Temp" to "98.4 °F"
                )
            )
        )
    )
    val diagnosticRecords: StateFlow<List<DiagnosticRecord>> = _diagnosticRecords.asStateFlow()

    private val _chatMessages = MutableStateFlow(
        listOf(
            ChatMessage(
                id = "msg_1",
                isFromUser = true,
                text = "What does “Echocardiogram with Doppler” actually involve, and will it hurt?",
                timestamp = "9:34 AM"
            ),
            ChatMessage(
                id = "msg_2",
                isFromUser = false,
                text = "Not at all! Completely gentle & pain-free. A sonographer applies warm gel and gently moves a smooth ultrasound wand across your chest. It takes 25–30 minutes, and you can breathe completely normally.",
                timestamp = "9:34 AM",
                visualPreviewUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBiizIY9Xml0AamMInyLnhloXaHvwqIuyJOQ7y9FwdPy_QbDWlecsMPNr09LMW7BEcOaEnZ8N-7964d2JHi2Mdipvkah6XqrKJtWTxSZUBC-RWqpOgqcqFq957qdfi4PRMEp2UeWlwiU1fMxFX-YFJApJIBOmh_ub_Txxcs8ROwWcI4lldPK1Eegh5DUJ_UfHX-fLhulkTWFmuhxRbaOIonwMfZqsfu7HOyOPObx9de",
                visualPreviewLabel = "Visual Preview: Gentle ultrasound scan",
                badge = "100% Painless",
                doctorContext = "Dr. Ananya Sen ordered this specifically to see how your heart chambers contract and pump blood.",
                audioDurationSeconds = 38
            ),
            ChatMessage(
                id = "msg_3",
                isFromUser = true,
                text = "What should I do about breakfast since I was told to fast?",
                timestamp = "9:38 AM"
            ),
            ChatMessage(
                id = "msg_4",
                isFromUser = false,
                text = "Good news! Since your morning blood draw was completed at 9:30 AM, Dr. Sen’s team noted that you can now enjoy light water and a snack before your 10:15 Echo.",
                timestamp = "9:38 AM",
                wayfindingLocation = "Directly beside Room 210 (35 steps left)",
                labTrackerSync = "Synchronised with Lab Tracker #L-409"
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _suggestedPrompts = MutableStateFlow(
        listOf(
            SuggestedPrompt(
                id = "p1",
                textEn = "Explain my ECG results in simple words",
                textHi = "मेरी ईसीजी रिपोर्ट को सरल शब्दों में समझाएं",
                icon = "monitor_heart"
            ),
            SuggestedPrompt(
                id = "p2",
                textEn = "Where do I pick up my prescription?",
                textHi = "मुझे अपनी दवा कहां से प्राप्त होगी?",
                icon = "prescriptions"
            ),
            SuggestedPrompt(
                id = "p3",
                textEn = "What questions should I ask Dr. Sen at discharge?",
                textHi = "डॉक्टर से परामर्श के दौरान क्या सवाल पूछने चाहिए?",
                icon = "help"
            ),
            SuggestedPrompt(
                id = "p4",
                textEn = "Can I take my blood pressure tablet now?",
                textHi = "क्या मैं अब अपनी बीपी की गोली ले सकती हूं?",
                icon = "medication"
            )
        )
    )
    val suggestedPrompts: StateFlow<List<SuggestedPrompt>> = _suggestedPrompts.asStateFlow()

    fun toggleLanguage() {
        _isHindiMode.update { !it }
    }

    fun selectTab(tabIndex: Int) {
        _currentTab.value = tabIndex
    }

    fun togglePrepItem(id: String) {
        _prepItems.update { list ->
            list.map { if (it.id == id) it.copy(isCompleted = !it.isCompleted) else it }
        }
    }

    fun toggleWalletPass() {
        _isWalletPassSaved.update { !it }
    }

    fun requestEscort() {
        _isEscortDispatched.value = true
        // Add log
        _attendantLogs.update { current ->
            listOf(
                AttendantLog(
                    id = "log_${System.currentTimeMillis()}",
                    type = "WhatsApp",
                    message = "Hospital Ambassador dispatched to Priya Sharma at Waiting Lounge B",
                    timestamp = "Just now"
                )
            ) + current
        }
    }

    fun requestSahayak() {
        _isSahayakRequested.value = true
        _showSahayakDialog.value = false
    }

    fun openSahayakDialog() {
        _showSahayakDialog.value = true
    }

    fun closeSahayakDialog() {
        _showSahayakDialog.value = false
    }

    fun openEmergencyDialog() {
        _showEmergencyDialog.value = true
    }

    fun closeEmergencyDialog() {
        _showEmergencyDialog.value = false
    }

    fun openAddAttendantDialog() {
        _showAddAttendantDialog.value = true
    }

    fun closeAddAttendantDialog() {
        _showAddAttendantDialog.value = false
    }

    fun openNurseCallDialog() {
        _showNurseCallDialog.value = true
    }

    fun closeNurseCallDialog() {
        _showNurseCallDialog.value = false
    }

    fun toggleAudioPlayback(messageId: String) {
        if (_playingAudioMessageId.value == messageId) {
            _playingAudioMessageId.value = null
        } else {
            _playingAudioMessageId.value = messageId
        }
    }

    fun addAttendant(name: String, phone: String, relation: String) {
        _patientProfile.update {
            it.copy(
                attendantName = name,
                attendantPhone = phone,
                attendantRole = relation
            )
        }
        _attendantLogs.update { current ->
            listOf(
                AttendantLog(
                    id = "log_${System.currentTimeMillis()}",
                    type = "WhatsApp",
                    message = "Added attendant: $name ($phone) for live OPD visit updates",
                    timestamp = "Just now"
                )
            ) + current
        }
        _showAddAttendantDialog.value = false
    }

    fun sendMessage(userText: String) {
        if (userText.isBlank()) return
        val userMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            isFromUser = true,
            text = userText,
            timestamp = "Just now"
        )
        val aiResponse = generateAiResponse(userText)
        _chatMessages.update { it + userMsg + aiResponse }
    }

    private fun generateAiResponse(prompt: String): ChatMessage {
        val lower = prompt.lowercase()
        return when {
            lower.contains("ecg") -> {
                ChatMessage(
                    id = "ai_${System.currentTimeMillis()}",
                    isFromUser = false,
                    text = "Your 12-lead ECG completed at 9:15 AM shows a healthy, regular sinus rhythm with a resting heart rate of 68 bpm. All electrical intervals (PR, QRS, QTc) are well within normal ranges, and there are no signs of reduced blood flow.",
                    timestamp = "Just now",
                    badge = "Normal Sinus Rhythm",
                    doctorContext = "Dr. Ananya Sen reviewed this trace and approved proceeding to your echocardiogram."
                )
            }
            lower.contains("prescription") || lower.contains("pharmacy") || lower.contains("दवा") -> {
                ChatMessage(
                    id = "ai_${System.currentTimeMillis()}",
                    isFromUser = false,
                    text = "Your digital prescriptions will be sent automatically to Central Pharmacy Counter 4 on the Ground Floor once Dr. Sen completes your consultation at 11:15 AM. You can also pick up Ayushman / TPA cashless medicine packs there.",
                    timestamp = "Just now",
                    wayfindingLocation = "Ground Floor, Counter 4 (Adjacent to Exit Gate 2)"
                )
            }
            lower.contains("question") || lower.contains("discharge") || lower.contains("ask") -> {
                ChatMessage(
                    id = "ai_${System.currentTimeMillis()}",
                    isFromUser = false,
                    text = "Here are 3 helpful questions for Dr. Sen:\n1. 'How does my heart muscle function compare to last year?'\n2. 'Should I adjust my blood pressure tablet dosage?'\n3. 'When should I return for my next cardiac review?'",
                    timestamp = "Just now",
                    badge = "Doctor Questions Checklist",
                    doctorContext = "Sister Lakshmi will also provide a written discharge summary before you leave."
                )
            }
            lower.contains("water") || lower.contains("food") || lower.contains("fast") || lower.contains("breakfast") -> {
                ChatMessage(
                    id = "ai_${System.currentTimeMillis()}",
                    isFromUser = false,
                    text = "Your blood draw is done, so you may drink water freely. A complimentary hydration cooler is available right outside Room 210. For food, keep it light until after your ultrasound.",
                    timestamp = "Just now",
                    wayfindingLocation = "Hydration Point: Level 2 Corridor beside Room 210"
                )
            }
            else -> {
                ChatMessage(
                    id = "ai_${System.currentTimeMillis()}",
                    isFromUser = false,
                    text = "Dr. Ananya Sen and Sister Lakshmi's care plan confirms you are on track for Token A-42 in Room 214. The procedure is non-invasive and painless. Feel free to ask about any step of today's visit!",
                    timestamp = "Just now",
                    badge = "Verified Care Plan",
                    doctorContext = "Cardiology OPD Wing C • Apollo Health City"
                )
            }
        }
    }
}
