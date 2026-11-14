package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.ChatMessage
import com.example.data.model.NurseNavigator
import com.example.data.model.SuggestedPrompt
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun CareAiScreen(
    chatMessages: List<ChatMessage>,
    suggestedPrompts: List<SuggestedPrompt>,
    nurse: NurseNavigator,
    isHindi: Boolean,
    playingAudioId: String?,
    onToggleAudio: (String) -> Unit,
    onSendMessage: (String) -> Unit,
    onTalkToNurse: () -> Unit,
    onNavigateToHydration: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(chatMessages.size) {
        listState.animateScrollToItem(chatMessages.size)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("care_ai_screen")
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Prominent Persistent Safety & Reassurance Banner
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = SereneSurfaceContainerLow,
                    border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.8f)),
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth().testTag("safety_guardrails_banner")
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(SereneSecondary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.VerifiedUser,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = if (isHindi) "केयर AI गाइडेंस गार्डरेल्स" else "Care Guidance AI Guardrails",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = SerenePrimary
                                    )
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(9999.dp))
                                        .background(Color.White)
                                        .border(1.dp, SereneSecondaryContainer, RoundedCornerShape(9999.dp))
                                        .padding(horizontal = 7.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = if (isHindi) "सत्यापित केयर प्लान" else "Verified Care Plan",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SereneSecondary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp
                                        )
                                    )
                                }
                            }

                            Text(
                                text = if (isHindi)
                                    "केयर AI अधिकृत क्लिनिकल निर्देशों को सरल भाषा में समझाता है। यह कोई दवा नहीं बदलता। आपातकाल में तुरंत स्टाफ को सूचित करें।"
                                else
                                    "Care AI translates authorised clinical instructions into everyday language. It does not alter dosages or diagnose. In emergency, alert hospital staff immediately.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SereneOnSurfaceVariant,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp
                                ),
                                modifier = Modifier.padding(top = 3.dp)
                            )
                        }
                    }
                }
            }

            // 2. Nurse Navigator Human Escalation Banner with Doctor Consultation Illustration
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.7f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth().testTag("nurse_escalation_banner")
                ) {
                    Column {
                        // Hero bedside photo
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(125.dp)
                                .background(SereneSecondaryContainer)
                        ) {
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuAiltsB08NRQ4C9rE2t80Zdy_CXB5T9qWte-us5IxloeC2VVacAJtu9qtqFN9lzss4pT7k7f-VXLndnSQbKDuRhx7jpXVynmIOKqvbyt0HdG4850LZqiTFtzC2HzIsy5P_2d8-OZdySh-cNuJqHLPf0K3yLWLvm7aUKBcWIK6HMxAMAHXD_ZtYoectVK9FPNUzlo0Xud64kvMi1MM8-HG_nNdpGgybGe6Z867OJkET1",
                                contentDescription = "Bedside doctor consultation",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(Color.Transparent, Color.White.copy(alpha = 0.85f)),
                                            startY = 50f
                                        )
                                    )
                            )

                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(Color.White.copy(alpha = 0.9f))
                                    .border(1.dp, SereneSecondaryContainer, RoundedCornerShape(9999.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.HealthAndSafety,
                                        contentDescription = null,
                                        tint = SereneSecondary,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Text(
                                        text = if (isHindi) "समर्पित केयर टीम" else "Dedicated Care Team",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SerenePrimary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }
                        }

                        // Nurse Info row & Call button
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box {
                                    AsyncImage(
                                        model = nurse.photoUrl,
                                        contentDescription = nurse.name,
                                        modifier = Modifier
                                            .size(42.dp)
                                            .clip(CircleShape)
                                            .border(2.dp, SerenePrimaryContainer, CircleShape)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(11.dp)
                                            .clip(CircleShape)
                                            .background(SereneSuccess)
                                            .border(1.5.dp, Color.White, CircleShape)
                                            .align(Alignment.BottomEnd)
                                    )
                                }

                                Column {
                                    Text(
                                        text = nurse.title,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SereneSecondary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp
                                        )
                                    )
                                    Text(
                                        text = nurse.name,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = SereneOnSurface,
                                            fontSize = 14.sp
                                        )
                                    )
                                    Text(
                                        text = nurse.supervisingDoctor,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SereneOnSurfaceVariant,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }

                            Button(
                                onClick = onTalkToNurse,
                                colors = ButtonDefaults.buttonColors(containerColor = SereneSecondary),
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                                modifier = Modifier.testTag("talk_to_nurse_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isHindi) "नर्स से बात करें" else "Talk to Nurse",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Milestone Time Marker
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9999.dp))
                            .background(SereneSecondaryContainer.copy(alpha = 0.7f))
                            .border(1.dp, SereneSecondaryContainer, RoundedCornerShape(9999.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (isHindi) "आज की यात्रा • अपोलो पवेलियन" else "Today's Care Journey • Apollo Pavilion",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SerenePrimary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            // Interactive Conversation Stream
            items(chatMessages) { message ->
                ChatMessageItem(
                    message = message,
                    isHindi = isHindi,
                    isPlayingAudio = playingAudioId == message.id,
                    onToggleAudio = { onToggleAudio(message.id) },
                    onNavigateToHydration = onNavigateToHydration
                )
            }

            // 3. Visual Comfort Break: What's Next in your Visit
            item {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = SereneSurfaceContainerLow,
                    border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = SereneSecondary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = if (isHindi) "आपकी विज़िट का प्रवाह" else "Your Visit Flow",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = SerenePrimary
                                    )
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(SereneSecondaryContainer)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (isHindi) "समय पर" else "On Schedule",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SerenePrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Milestone 1
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White,
                                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.5f)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.TaskAlt,
                                        contentDescription = null,
                                        tint = SereneSecondary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = if (isHindi) "ब्लड टेस्ट" else "Lab Draw",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = SereneOnSurface
                                        )
                                    )
                                    Text(
                                        text = "Done 9:30",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SereneOnSurfaceVariant,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }

                            // Milestone 2 (Active)
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White,
                                border = androidx.compose.foundation.BorderStroke(2.dp, SereneSecondary),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = null,
                                        tint = SereneSecondary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = if (isHindi) "इको डॉपलर" else "Echo Doppler",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = SerenePrimary
                                        )
                                    )
                                    Text(
                                        text = "10:15 AM",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SereneSecondary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }

                            // Milestone 3
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White.copy(alpha = 0.7f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.4f)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MeetingRoom,
                                        contentDescription = null,
                                        tint = SereneOutline,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = if (isHindi) "डॉ. परामर्श" else "Dr. Review",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            color = SereneOnSurface
                                        )
                                    )
                                    Text(
                                        text = "11:00 AM",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SereneOnSurfaceVariant,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 4. Suggested Plain-Language Prompts
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = SereneSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = if (isHindi) "सुझाए गए सरल प्रश्न" else "Suggested Plain-Language Prompts",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = SerenePrimary
                                )
                            )
                        }

                        Text(
                            text = if (isHindi) "पूछने के लिए टैप करें" else "Tap to ask",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SereneOnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }

                    suggestedPrompts.forEach { prompt ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White,
                            border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.8f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    onSendMessage(if (isHindi) prompt.textHi else prompt.textEn)
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(SereneSecondaryContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.HelpOutline,
                                            contentDescription = null,
                                            tint = SereneSecondary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    Text(
                                        text = if (isHindi) prompt.textHi else prompt.textEn,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = SereneOnSurface,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 13.sp
                                        )
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.NorthEast,
                                    contentDescription = "Ask",
                                    tint = SereneSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Bottom Input Bar
        Surface(
            color = Color.White,
            shadowElevation = 8.dp,
            border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth().padding(bottom = 76.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = {
                        Text(
                            text = if (isHindi) "आज की जांच या डॉक्टर के बारे में पूछें..." else "Ask about your visit, doctor, or test...",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp)
                        )
                    },
                    shape = RoundedCornerShape(9999.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SereneSecondary,
                        unfocusedBorderColor = SereneSecondaryContainer,
                        focusedContainerColor = SereneSurfaceContainerLow,
                        unfocusedContainerColor = SereneSurfaceContainerLow
                    ),
                    singleLine = true,
                    modifier = Modifier.weight(1f).testTag("care_ai_input_field")
                )

                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            onSendMessage(inputText)
                            inputText = ""
                        }
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(if (inputText.isNotBlank()) SereneSecondary else SereneSecondaryContainer)
                        .testTag("care_ai_send_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = if (inputText.isNotBlank()) Color.White else SerenePrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatMessageItem(
    message: ChatMessage,
    isHindi: Boolean,
    isPlayingAudio: Boolean,
    onToggleAudio: () -> Unit,
    onNavigateToHydration: () -> Unit
) {
    if (message.isFromUser) {
        // Patient Bubble (Right-aligned dark blue)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${if (isHindi) "आप" else "You"} • ${message.timestamp}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp,
                    color = SereneOnSurfaceVariant
                ),
                modifier = Modifier.padding(bottom = 3.dp, end = 4.dp)
            )
            Surface(
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
                color = SerenePrimary,
                shadowElevation = 2.dp,
                modifier = Modifier.widthIn(max = 300.dp)
            ) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    ),
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                )
            }
        }
    } else {
        // AI Companion Response Card (Left-aligned white card)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(bottom = 4.dp, start = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(SereneSecondary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SmartToy,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
                Text(
                    text = "Care AI Companion",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = SerenePrimary,
                        fontSize = 12.sp
                    )
                )
                Text(
                    text = message.timestamp,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = SereneOutline,
                        fontSize = 10.sp
                    )
                )
            }

            Surface(
                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 18.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer),
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Optional Visual Preview Image
                    if (message.visualPreviewUrl != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, SereneSecondaryContainer, RoundedCornerShape(12.dp))
                        ) {
                            AsyncImage(
                                model = message.visualPreviewUrl,
                                contentDescription = message.visualPreviewLabel ?: "Visual Preview",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            // Bottom caption pill
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.White.copy(alpha = 0.95f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MonitorHeart,
                                        contentDescription = null,
                                        tint = SereneSecondary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = message.visualPreviewLabel ?: "Visual Preview",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SerenePrimary,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 11.sp
                                        )
                                    )
                                }

                                if (message.badge != null) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(9999.dp))
                                            .background(SereneSuccessContainer)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = message.badge,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = SereneOnSuccess,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 9.sp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Main response text
                    Text(
                        text = message.text,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = SereneOnSurface,
                            fontSize = 13.sp,
                            lineHeight = 20.sp
                        )
                    )

                    // Optional Doctor Context Card
                    if (message.doctorContext != null) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = SereneSurfaceContainerLow,
                            border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.6f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MedicalServices,
                                    contentDescription = null,
                                    tint = SereneSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Column {
                                    Text(
                                        text = if (isHindi) "डॉक्टर का क्लिनिकल संदर्भ" else "Doctor's Clinical Context",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = SerenePrimary,
                                            fontSize = 11.sp
                                        )
                                    )
                                    Text(
                                        text = message.doctorContext,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SereneOnSurfaceVariant,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }
                        }
                    }

                    // Optional Wayfinding Location Card (e.g. Hydration Point)
                    if (message.wayfindingLocation != null) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = SereneSurfaceContainerLow,
                            border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onNavigateToHydration() }
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(SereneSecondary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.LocalCafe,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(15.dp)
                                        )
                                    }
                                    Column {
                                        Text(
                                            text = if (isHindi) "मुफ्त जलपान व पेयजल स्टेशन" else "Complimentary Hydration Station",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = SerenePrimary,
                                                fontSize = 11.sp
                                            )
                                        )
                                        Text(
                                            text = message.wayfindingLocation,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = SereneOnSurfaceVariant,
                                                fontSize = 10.sp
                                            )
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = SereneSecondary
                                )
                            }
                        }
                    }

                    // Action tools row: Audio listen, bookmark, helpful
                    Divider(color = SereneSecondaryContainer.copy(alpha = 0.5f), thickness = 1.dp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (message.audioDurationSeconds != null) {
                            Surface(
                                shape = RoundedCornerShape(9999.dp),
                                color = if (isPlayingAudio) SereneSecondary else SereneSurfaceContainerLow,
                                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(9999.dp))
                                    .clickable { onToggleAudio() }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isPlayingAudio) Icons.Default.Pause else Icons.Default.VolumeUp,
                                        contentDescription = "Listen",
                                        tint = if (isPlayingAudio) Color.White else SereneSecondary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = if (isPlayingAudio)
                                            (if (isHindi) "प्ले हो रहा है..." else "Playing audio...")
                                        else
                                            (if (isHindi) "सुनें (0:38)" else "Listen (0:38)"),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = if (isPlayingAudio) Color.White else SereneSecondary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }
                        } else if (message.labTrackerSync != null) {
                            Text(
                                text = message.labTrackerSync,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SereneOnSurfaceVariant,
                                    fontSize = 10.sp
                                )
                            )
                        } else {
                            Text(
                                text = "Care AI Certified",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SereneOnSurfaceVariant,
                                    fontSize = 10.sp
                                )
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            var isThumbed by remember { mutableStateOf(false) }
                            var isBookmarked by remember { mutableStateOf(false) }

                            IconButton(
                                onClick = { isThumbed = !isThumbed },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = if (isThumbed) Icons.Default.ThumbUp else Icons.Default.ThumbUpOffAlt,
                                    contentDescription = "Helpful",
                                    tint = if (isThumbed) SereneSecondary else SereneOutline,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            IconButton(
                                onClick = { isBookmarked = !isBookmarked },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Save to visit notes",
                                    tint = if (isBookmarked) SereneSecondary else SereneOutline,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
