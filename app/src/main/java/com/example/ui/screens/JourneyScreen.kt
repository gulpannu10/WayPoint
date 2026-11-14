package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.PreparationItem
import com.example.data.model.StepStatus
import com.example.data.model.TimelineStep
import com.example.ui.theme.*

@Composable
fun JourneyScreen(
    timelineSteps: List<TimelineStep>,
    prepItems: List<PreparationItem>,
    isHindi: Boolean,
    attendantName: String,
    onNavigateToRoute: () -> Unit,
    onViewRecords: () -> Unit,
    onTogglePrepItem: (String) -> Unit,
    onRequestSahayak: () -> Unit
) {
    var isPrepExpanded by remember { mutableStateOf(false) }

    // Pulsing animation for the active step
    val infiniteTransition = rememberInfiniteTransition(label = "pulseTimeline")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseTimelineAlpha"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("journey_screen")
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Unified Status & Journey Progress Anchor
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SereneSurfaceContainerLow),
                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.8f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth().testTag("active_pathway_card")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = if (isHindi) "सक्रिय ओपीडी पाथवे" else "ACTIVE OPD PATHWAY",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SereneSecondary,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp
                                    )
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(9999.dp))
                                        .background(SereneSecondaryContainer)
                                        .border(1.dp, SerenePrimaryContainer.copy(alpha = 0.3f), RoundedCornerShape(9999.dp))
                                        .padding(horizontal = 7.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = if (isHindi) "आगे ३ मरीज हैं" else "3 patients ahead",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SerenePrimary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }
                            Text(
                                text = if (isHindi) "चरण २ / ४: डायग्नोस्टिक इमेजिंग" else "Stage 2 of 4: Diagnostic Imaging",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SerenePrimary
                                )
                            )
                        }

                        // Time pill
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(Color.White)
                                .border(1.dp, SereneSecondaryContainer, RoundedCornerShape(9999.dp))
                                .padding(horizontal = 9.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = SereneSecondary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Est. 11:45 AM",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = SereneSecondary
                                )
                            )
                        }
                    }

                    Text(
                        text = if (isHindi)
                            "कार्डियोलॉजी ओपीडी विंग सी • अपोलो हेल्थ सिटी। टोकन ए-४२ कमरा २१४ में अगला।"
                        else
                            "Cardiology OPD Wing C • Apollo Health City. Token A-42 called next in Room 214.",
                        style = MaterialTheme.typography.bodySmall.copy(color = SereneOnSurfaceVariant)
                    )

                    // 4-Stage Progress Bars
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            // Step 1: Done
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(SereneSecondary)
                            )
                            // Step 2: In progress
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(SerenePrimaryContainer.copy(alpha = pulseAlpha))
                            )
                            // Step 3: Upcoming
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(SereneSecondaryContainer)
                            )
                            // Step 4: Upcoming
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(SereneSecondaryContainer)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "1. Triage ✓",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SereneSecondary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            )
                            Text(
                                text = "2. Echo (Now)",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SerenePrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            )
                            Text(
                                text = "3. Doctor",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SereneOutline,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 11.sp
                                )
                            )
                            Text(
                                text = "4. Pharmacy",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SereneOutline,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    // Attendant WhatsApp Sync
                    Divider(color = SereneSecondaryContainer.copy(alpha = 0.7f), thickness = 1.dp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(CircleShape)
                                    .background(SereneSecondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Group,
                                    contentDescription = null,
                                    tint = SereneSecondary,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                            Text(
                                text = if (isHindi) "परिवार अपडेट: $attendantName (तीमारदार)" else "WhatsApp Family Updates: $attendantName (Spouse)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SereneOnSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            )
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(Color.White)
                                .border(1.dp, SereneSecondaryContainer, RoundedCornerShape(9999.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = null,
                                tint = SereneWhatsApp,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "WhatsApp Sync",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SerenePrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        // 2. Dominant 'Next Confirmed Step' Hero Card with Friendly Procedural Photo
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth().testTag("next_confirmed_step_card")
            ) {
                Column {
                    // Image container with gradient and overlays
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(175.dp)
                            .background(SereneSecondaryContainer)
                    ) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuCOrYaYPnNeCTqB2RVGc5-sbH_FCL9TINOFlXtRsOUhDEk5D7cFFMZa1-gTRc7lrFWhwz3pUtw8qA0kUcMUv-26S5PrCFuxqhioXedbtKAc-WOyUw1Z2WpC1LYwvEWa5AvpUYGTHbL20aGuBKme8EkUTv35C8r8kwu9MCik0UiL7OjxdiReAj1tkB_JzaTngLX1T5iGetpQ7coOJwrm73Wbk5BkGxHjZz98b6SjCX_L",
                            contentDescription = "Corridor wayfinding to Room 214",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Dark gradient overlay at bottom
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.65f)),
                                        startY = 60f
                                    )
                                )
                        )

                        // Overlays
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(SereneSecondary)
                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PinDrop,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = if (isHindi) "अगला: टोकन ए-४२" else "Next: Token A-42",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(Color.White.copy(alpha = 0.95f))
                                    .padding(horizontal = 9.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Alarm,
                                    contentDescription = null,
                                    tint = SereneSecondary,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = if (isHindi) "१५ मिनट में (10:15 AM)" else "In 15 mins (10:15 AM)",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SereneSecondary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }

                    // Card Body
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isHindi) "हार्ट अल्ट्रासाउंड / इकोकार्डियोग्राफी" else "Heart Ultrasound / Echo",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SerenePrimary
                                ),
                                modifier = Modifier.weight(1f)
                            )

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(SereneSecondaryContainer)
                                    .padding(horizontal = 9.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = if (isHindi) "दर्द रहित" else "Painless / दर्द रहित",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SereneSecondary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Text(
                            text = if (isHindi)
                                "टेक्नोलॉजिस्ट मार्कस एस. के साथ सौम्य नॉन-इनवेसिव डॉपलर ध्वनि तरंग स्कैन।"
                            else
                                "Gentle non-invasive Doppler sound-wave scan with technologist Marcus S.",
                            style = MaterialTheme.typography.bodySmall.copy(color = SereneOnSurfaceVariant)
                        )

                        // Room indicator box
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = SereneSurfaceContainerLow,
                            border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.8f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MeetingRoom,
                                        contentDescription = null,
                                        tint = SereneSecondary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = if (isHindi) "कमरा २१४ • विंग सी डायग्नोस्टिक" else "कमरा २१४ • Room 214 (Wing C)",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = SerenePrimary
                                        )
                                    )
                                }

                                Text(
                                    text = "Token A-42",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SereneSecondary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        // Nursing Verification row
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = SereneSurfaceContainerLow.copy(alpha = 0.6f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.5f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = null,
                                        tint = SereneSecondary,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Text(
                                        text = if (isHindi) "नर्सिंग डेस्क २बी सत्यापित • मशीन कैलिब्रेटेड" else "Nursing Desk 2B Confirmed • Ready",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SereneOnSurfaceVariant,
                                            fontSize = 11.sp
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
                                        text = if (isHindi) "अगला नंबर" else "Turn Next",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SereneSecondary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }
                        }

                        // Action Buttons
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = onNavigateToRoute,
                                colors = ButtonDefaults.buttonColors(containerColor = SereneSecondary),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("guide_route_room_214_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.NearMe,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isHindi) "कमरा २१४ का मार्ग देखें" else "Guide Route to Room 214 / कमरा २१४ मार्ग",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }

                            FilledTonalButton(
                                onClick = { isPrepExpanded = !isPrepExpanded },
                                colors = ButtonDefaults.filledTonalButtonColors(
                                    containerColor = SereneSecondaryContainer,
                                    contentColor = SerenePrimary
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth().testTag("toggle_prep_checklist_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Checklist,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isPrepExpanded)
                                        (if (isHindi) "तैयारी चेकलिस्ट छुपाएं" else "Hide Prep Checklist")
                                    else
                                        (if (isHindi) "तैयारी चेकलिस्ट देखें (${prepItems.size} बातें)" else "View Full Prep Checklist (${prepItems.size} items)"),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        // Expandable Preparation Checklist
                        AnimatedVisibility(
                            visible = isPrepExpanded,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = SereneSurfaceContainerLow,
                                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer),
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = if (isHindi) "अल्ट्रासाउंड से पहले निर्देश:" else "Pre-Scan Instructions:",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = SerenePrimary
                                        )
                                    )

                                    prepItems.forEach { item ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(Color.White)
                                                .border(
                                                    1.dp,
                                                    if (item.isCrucial) SereneSecondaryContainer else SereneOutlineVariant.copy(alpha = 0.5f),
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .clickable { onTogglePrepItem(item.id) }
                                                .padding(horizontal = 10.dp, vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Checkbox(
                                                checked = item.isCompleted,
                                                onCheckedChange = { onTogglePrepItem(item.id) },
                                                colors = CheckboxDefaults.colors(checkedColor = SereneSecondary),
                                                modifier = Modifier.size(20.dp)
                                            )

                                            Text(
                                                text = if (isHindi) item.textHi else item.textEn,
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    fontSize = 12.sp,
                                                    color = if (item.isCompleted) SereneOnSurfaceVariant else SereneOnSurface,
                                                    textDecoration = if (item.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                                                    fontWeight = if (item.isCrucial) FontWeight.Bold else FontWeight.Normal
                                                ),
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3. Streamlined Subtle Background Status Strip
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = SereneSurfaceContainerLow.copy(alpha = 0.85f),
                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.7f)),
                shadowElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { onViewRecords() }
                    .testTag("blood_test_status_strip")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(SereneSecondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = "Sync",
                            tint = SereneSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = if (isHindi) "रूटीन ब्लड टेस्ट" else "Routine Blood Test",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = SerenePrimary
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SereneSecondaryContainer)
                                    .padding(horizontal = 5.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = if (isHindi) "लैब में प्रोसेसिंग" else "Processing in lab",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SereneSecondary
                                    )
                                )
                            }
                        }
                        Text(
                            text = if (isHindi)
                                "प्रयोगशाला में प्रक्रिया जारी है। रिपोर्ट स्वतः डॉक्टर और आपके फोन पर आएगी।"
                            else
                                "Processing in laboratory. No action needed now. Results will arrive automatically.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SereneOnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }

                    Text(
                        text = "~35m left",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = SereneSecondary,
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }

        // 4. Linear Journey Timeline ('What has happened / Where you are / What comes next')
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.8f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth().testTag("linear_journey_timeline")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isHindi) "आज की ओपीडी समयरेखा" else "Today's OPD Timeline",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SerenePrimary
                                )
                            )
                            Text(
                                text = if (isHindi) "प्रिया शर्मा का स्वास्थ्य मार्ग" else "Care progression for Priya Sharma (ABHA #91-8472-1049-2810)",
                                style = MaterialTheme.typography.bodySmall.copy(color = SereneOnSurfaceVariant, fontSize = 11.sp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(SereneSecondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timeline,
                                contentDescription = null,
                                tint = SereneSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Timeline Items
                    timelineSteps.forEachIndexed { index, step ->
                        TimelineStepRow(
                            step = step,
                            isHindi = isHindi,
                            isLast = index == timelineSteps.lastIndex,
                            pulseAlpha = pulseAlpha
                        )
                    }
                }
            }
        }

        // 5. Consolidated Non-Intrusive Quick Assist Bar
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = SereneSurfaceContainerLow,
                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(SereneSecondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Accessible,
                                contentDescription = null,
                                tint = SereneSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = if (isHindi) "व्हीलचेयर या सहायक की आवश्यकता है?" else "Need wheelchair or porter assist?",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SerenePrimary,
                                    fontSize = 12.sp
                                )
                            )
                            Text(
                                text = if (isHindi) "ओपीडी सहायक हेल्पडेस्क २बी पर तैनात हैं" else "OPD Sahayak stationed at Helpdesk 2B",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SereneOnSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    Button(
                        onClick = onRequestSahayak,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("request_sahayak_button")
                    ) {
                        Text(
                            text = if (isHindi) "सहायक बुलाएं" else "Request Sahayak",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = SereneSecondary
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TimelineStepRow(
    step: TimelineStep,
    isHindi: Boolean,
    isLast: Boolean,
    pulseAlpha: Float
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Left indicator column with line
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(26.dp)
        ) {
            when (step.status) {
                StepStatus.COMPLETED -> {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(SereneSecondary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Completed",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
                StepStatus.IN_PROGRESS -> {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(SerenePrimary)
                            .border(3.dp, SereneSecondaryContainer.copy(alpha = pulseAlpha), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                        )
                    }
                }
                StepStatus.UPCOMING -> {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(SereneSurfaceContainerLow)
                            .border(1.5.dp, SereneOutlineVariant, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(SereneOutline)
                        )
                    }
                }
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(70.dp)
                        .background(SereneSecondaryContainer)
                )
            }
        }

        // Right Content Card
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = when (step.status) {
                StepStatus.IN_PROGRESS -> SereneSurfaceContainerLow
                StepStatus.COMPLETED -> SereneSurfaceContainerLow.copy(alpha = 0.5f)
                StepStatus.UPCOMING -> Color.White
            },
            border = androidx.compose.foundation.BorderStroke(
                width = if (step.status == StepStatus.IN_PROGRESS) 1.5.dp else 1.dp,
                color = if (step.status == StepStatus.IN_PROGRESS) SerenePrimaryContainer else SereneSecondaryContainer
            ),
            modifier = Modifier.weight(1f).padding(bottom = if (isLast) 0.dp else 12.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isHindi) step.titleHi else step.titleEn,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = if (step.status == StepStatus.UPCOMING) SereneOnSurface else SerenePrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    val statusBadgeText = when (step.status) {
                        StepStatus.COMPLETED -> if (isHindi) "पूर्ण" else "[COMPLETED]"
                        StepStatus.IN_PROGRESS -> if (isHindi) "टोकन ए-४२ • जारी" else "[TOKEN A-42 • IN PROGRESS]"
                        StepStatus.UPCOMING -> if (isHindi) "आगामी" else "[UPCOMING]"
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9999.dp))
                            .background(
                                when (step.status) {
                                    StepStatus.COMPLETED -> SereneSecondaryContainer
                                    StepStatus.IN_PROGRESS -> SereneSecondaryContainer
                                    StepStatus.UPCOMING -> SereneSurfaceContainerLow
                                }
                            )
                            .padding(horizontal = 7.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = statusBadgeText,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (step.status == StepStatus.UPCOMING) SereneOnSurfaceVariant else SerenePrimary,
                                fontSize = 9.sp
                            )
                        )
                    }
                }

                Text(
                    text = "${step.staffLead} • ${step.timeLabel}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = SereneOnSurfaceVariant,
                        fontSize = 11.sp
                    )
                )

                if (step.detailNoteEn.isNotBlank()) {
                    Text(
                        text = if (isHindi) step.detailNoteHi else step.detailNoteEn,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (step.status == StepStatus.IN_PROGRESS) SereneSecondary else SereneOnSurfaceVariant,
                            fontSize = 11.sp,
                            fontWeight = if (step.status == StepStatus.IN_PROGRESS) FontWeight.SemiBold else FontWeight.Normal
                        )
                    )
                }

                // Vitals Badges if any
                if (step.vitalsBadges.isNotEmpty()) {
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        step.vitalsBadges.forEach { badge ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(Color.White)
                                    .border(1.dp, SereneSecondaryContainer, RoundedCornerShape(9999.dp))
                                    .padding(horizontal = 7.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = badge,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        color = SerenePrimary,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
