package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Amenity
import com.example.data.model.AttendantLog
import com.example.data.model.PatientProfile
import com.example.ui.theme.*

@Composable
fun VisitGuideScreen(
    profile: PatientProfile,
    attendantLogs: List<AttendantLog>,
    amenities: List<Amenity>,
    isHindi: Boolean,
    isWalletSaved: Boolean,
    isEscortDispatched: Boolean,
    onToggleWallet: () -> Unit,
    onRequestEscort: () -> Unit,
    onAddAttendant: () -> Unit,
    onUrgentHelp: () -> Unit
) {
    var showWhatsAppToast by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("visit_guide_screen")
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Interactive Digital Fast-Track Pass
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier.fillMaxWidth().testTag("digital_fast_track_pass")
            ) {
                Column {
                    // Top gradient strip
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(SereneSecondary, SereneSecondaryContainer, SerenePrimary)
                                )
                            )
                    )

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = null,
                                        tint = SereneSecondary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = if (isHindi) "डिजिटल ओपीडी फास्ट-ट्रैक पास" else "DIGITAL OPD FAST-TRACK PASS",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SereneSecondary,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 0.5.sp,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                                Text(
                                    text = profile.name,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = SereneOnSurface
                                    )
                                )
                                Text(
                                    text = "ABHA: ${profile.abhaId}\nUHID: ${profile.uhid} • Age: ${profile.ageGender}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = SereneOnSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                )
                            }

                            // Active token ready pill
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(SereneSecondaryContainer)
                                    .padding(horizontal = 9.dp, vertical = 4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(7.dp)
                                            .clip(CircleShape)
                                            .background(SereneSecondary)
                                    )
                                    Text(
                                        text = if (isHindi) "इको लैब तैयार" else "Echo Lab Ready",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = SerenePrimary,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }
                        }

                        // Barcode & QR Kiosk Scanner container
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = SereneSurfaceContainerLow,
                            border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.6f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.4f)),
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        BarcodeCanvas(
                                            modifier = Modifier
                                                .width(220.dp)
                                                .height(44.dp)
                                        )
                                        Text(
                                            text = "ABDM • SCAN & SHARE KIOSK PASS",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontSize = 9.sp,
                                                letterSpacing = 1.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SereneOnSurfaceVariant
                                            ),
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                    }
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.QrCodeScanner,
                                        contentDescription = null,
                                        tint = SereneSecondary,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Text(
                                        text = if (isHindi) "ओपीडी स्टेशन ३ कियोस्क पर स्कैन करें" else "Scan at OPD Station 3 / Kiosk for Instant Token",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SereneOnSurfaceVariant,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }
                        }

                        // Pass Actions
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { showWhatsAppToast = true },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = SerenePrimary),
                                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer),
                                modifier = Modifier.weight(1f).testTag("whatsapp_pass_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (showWhatsAppToast) (if (isHindi) "शेयर किया गया!" else "Pass Shared!") else "WhatsApp Pass",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Button(
                                onClick = onToggleWallet,
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isWalletSaved) SereneSecondaryContainer else SerenePrimary,
                                    contentColor = if (isWalletSaved) SerenePrimary else Color.White
                                ),
                                modifier = Modifier.weight(1f).testTag("save_pass_wallet_button")
                            ) {
                                Icon(
                                    imageVector = if (isWalletSaved) Icons.Default.CheckCircle else Icons.Default.AccountBalanceWallet,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isWalletSaved) (if (isHindi) "वॉलेट में सुरक्षित" else "Pass Saved") else (if (isHindi) "पास सेव करें" else "Save Pass"),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. Hospital Indoor Wayfinding Map Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth().testTag("floor_navigation_card")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(SereneSecondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Navigation,
                                    contentDescription = null,
                                    tint = SerenePrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = if (isHindi) "फ्लोर नेविगेशन" else "Floor Navigation",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = SereneOnSurface
                                    )
                                )
                                Text(
                                    text = "Apollo Pavilion • Level 2",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = SereneOnSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(SereneSecondaryContainer)
                                .padding(horizontal = 9.dp, vertical = 4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DirectionsWalk,
                                    contentDescription = null,
                                    tint = SerenePrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "80m (2 min)",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SerenePrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }

                    // Interactive Schematic Floor Plan Canvas
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(210.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(SereneSurfaceContainerLow)
                            .border(1.dp, SereneSecondaryContainer, RoundedCornerShape(14.dp))
                    ) {
                        HospitalFloorPlanCanvas(
                            modifier = Modifier.fillMaxSize(),
                            isHindi = isHindi
                        )

                        // Precision Callout at bottom
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White.copy(alpha = 0.95f),
                            shadowElevation = 2.dp,
                            border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.8f)),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Explore,
                                        contentDescription = null,
                                        tint = SereneSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = if (isHindi) "सेंट्रल फार्मेसी के आगे दाहिने मुड़ें" else "Turn right past Central Pharmacy",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SereneOnSurface,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                                Text(
                                    text = if (isHindi) "कदम १ / २" else "Step 1 of 2",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SereneSecondary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }
                    }

                    // Featured Corridor Visual Guide Photo
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Signpost,
                                    contentDescription = null,
                                    tint = SereneSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = if (isHindi) "कमरा २१४ का विजुअल गाइड" else "How to get to Room 214 - Heart Ultrasound",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = SereneOnSurface
                                    )
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(SereneSecondaryContainer)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Visual View",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SerenePrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp
                                    )
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, SereneSecondaryContainer, RoundedCornerShape(12.dp))
                        ) {
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuCOrYaYPnNeCTqB2RVGc5-sbH_FCL9TINOFlXtRsOUhDEk5D7cFFMZa1-gTRc7lrFWhwz3pUtw8qA0kUcMUv-26S5PrCFuxqhioXedbtKAc-WOyUw1Z2WpC1LYwvEWa5AvpUYGTHbL20aGuBKme8EkUTv35C8r8kwu9MCik0UiL7OjxdiReAj1tkB_JzaTngLX1T5iGetpQ7coOJwrm73Wbk5BkGxHjZz98b6SjCX_L",
                                contentDescription = "Corridor wayfinding signboards",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(Color.Transparent, SerenePrimary.copy(alpha = 0.85f)),
                                            startY = 50f
                                        )
                                    )
                            )

                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Signpost,
                                    contentDescription = null,
                                    tint = SereneSecondaryContainer,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = if (isHindi)
                                        "ओपीडी विंग सी साइनबोर्ड का पालन करें (कमरा २१४ - इकोकार्डियोग्राफी)"
                                    else
                                        "Follow OPD Wing C signboards to Room 214 (कमरा २१४ - इकोकार्डियोग्राफी)",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }

                    // Step Landmark Note
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SereneSurfaceContainerLow,
                        border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SereneSecondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.TurnRight,
                                    contentDescription = null,
                                    tint = SerenePrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Column {
                                Text(
                                    text = if (isHindi) "इको लैब नर्सिंग स्टेशन ३ के ठीक सामने है (कमरा २१४)" else "Echo Lab is directly across Nursing Station 3 (कमरा २१४)",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = SereneOnSurface
                                    )
                                )
                                Text(
                                    text = if (isHindi)
                                        "ऊपर लगे द्विभाषी बोर्ड 'OPD Wing C • इकोकार्डियोग्राफी' को देखें। टील स्क्रब में नर्सिंग स्टाफ मार्गदर्शन करेगा।"
                                    else
                                        "Look for the bilingual overhead board 'OPD Wing C • इकोकार्डियोग्राफी'. Nursing staff in teal scrubs will guide you inside.",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = SereneOnSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }

                    // Destination Room Preview Card
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Visibility,
                                        contentDescription = null,
                                        tint = SereneSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = if (isHindi) "गंतव्य पूर्वावलोकन: कमरा २१४ अंदर" else "Destination Preview: Room 214 Inside",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = SereneOnSurface
                                        )
                                    )
                                }
                                Text(
                                    text = if (isHindi) "शांत सुइट" else "Cozy Suite",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SereneSecondary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                AsyncImage(
                                    model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBIn2PU4oGem2JrPaIBB5OffiGPp3kQZ5Qh2ROwkp1OmFsYmoQqfiIV0opgL3UMmO0buxmolTySzxaEk986DvtDfJcuwxTuswYDgLYASnCx1iWW6pFcSlHZweDLwyuo_930d7Sfh-1H_TaJlTKyEGAXq8YycaTSBZr0Yj18RjwBTAymcnLJVH2jKiDeVuaD653KaHOJ_Zq7Oa5l51kXMOMmV8TQah8RLQ70pfqyyCWL",
                                    contentDescription = "Room 214 preview",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )

                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color.White.copy(alpha = 0.95f),
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .fillMaxWidth()
                                        .padding(6.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = if (isHindi) "स्कैन के लिए तैयार अल्ट्रासाउंड सुइट" else "Gentle ultrasound suite prepared for your scan",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = 10.sp,
                                                color = SereneOnSurface
                                            )
                                        )
                                        Text(
                                            text = if (isHindi) "हीटेड बेड तैयार" else "Heated Bed Ready",
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

                    // Accessibility badge
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SereneSurfaceContainerLow,
                        border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccessibleForward,
                                contentDescription = null,
                                tint = SereneSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = if (isHindi) "व्हीलचेयर स्टेप-फ्री कॉरिडोर • लिफ्ट बैंक बी, लेवल २" else "Wheelchair step-free corridor • Elevator Bank B, Level 2",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SereneOnSurface,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        // 3. Family Attendant Link & Real-Time Sync Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth().testTag("family_attendant_link_card")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(SereneSecondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FamilyRestroom,
                                    contentDescription = null,
                                    tint = SerenePrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = if (isHindi) "परिवार अटेंडेंट लिंक" else "Family Attendant Link",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = SereneOnSurface
                                    )
                                )
                                Text(
                                    text = if (isHindi) "लाइव विज़िट ट्रैकिंग और WhatsApp संदेश" else "Live Visit Tracking & WhatsApp Alerts",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = SereneOnSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(SereneSecondaryContainer)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Active Sync",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SerenePrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }

                    // Attendant pill
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SereneSurfaceContainerLow,
                        border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(SerenePrimary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "RS",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }

                                Column {
                                    Text(
                                        text = "${profile.attendantName} (${profile.attendantRole})",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = SereneOnSurface
                                        )
                                    )
                                    Text(
                                        text = "${profile.attendantPhone} • Authorized Attendant",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SereneOnSurfaceVariant,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(SereneWhatsAppContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Chat,
                                    contentDescription = "WhatsApp",
                                    tint = SereneWhatsApp,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    // Logs sent to attendant
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = if (isHindi) "राजेश को भेजे गए लाइव संदेश लॉग:" else "WhatsApp & SMS Log Sent to Rajesh:",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SereneOnSurfaceVariant,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )

                        attendantLogs.forEach { log ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = SereneSurfaceContainerLow.copy(alpha = 0.6f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.3f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(SereneSecondary)
                                        )
                                        Text(
                                            text = log.message,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = 11.sp,
                                                color = SereneOnSurface
                                            )
                                        )
                                    }
                                    Text(
                                        text = log.timestamp,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SereneOutline,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }
                        }
                    }

                    OutlinedButton(
                        onClick = onAddAttendant,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().testTag("add_attendant_button")
                    ) {
                        Icon(imageVector = Icons.Default.ManageAccounts, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isHindi) "अटेंडेंट / WhatsApp नंबर बदलें" else "Add Attendant / WhatsApp Number",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // 4. Escort & Staff Assistance Bento Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SerenePrimary),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth().testTag("escort_assistance_card")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AssistWalker,
                                contentDescription = null,
                                tint = SereneSecondaryContainer,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column {
                            Text(
                                text = if (isHindi) "एस्कॉर्ट या व्हीलचेयर चाहिए?" else "Need an Escort or Wheelchair?",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = if (isHindi) "अस्पताल के राजदूत आपकी सहायता के लिए तैयार हैं।" else "Friendly hospital ambassadors are on standby.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White.copy(alpha = 0.12f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = null,
                                    tint = SereneSecondaryContainer,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = if (isEscortDispatched)
                                        (if (isHindi) "सहायक रवाना: ~२ मिनट" else "Escort Dispatched: ~2 mins")
                                    else
                                        (if (isHindi) "अनुमानित आगमन: ~३ मिनट" else "Est. staff arrival: ~3 mins"),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }

                            Button(
                                onClick = onRequestEscort,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isEscortDispatched) SereneSecondaryContainer else Color.White,
                                    contentColor = SerenePrimary
                                ),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                enabled = !isEscortDispatched,
                                modifier = Modifier.testTag("request_escort_now_button")
                            ) {
                                Text(
                                    text = if (isEscortDispatched) (if (isHindi) "डिस्पैच हुआ ✓" else "Dispatched ✓") else (if (isHindi) "अभी बुलाएं" else "Request Now"),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // 5. Hospital Amenities Directory Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isHindi) "परिसर सुविधाएं" else "Campus Amenities",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = SerenePrimary
                        )
                    )
                    Text(
                        text = if (isHindi) "पवेलियन डायरेक्टरी" else "Pavilion Directory",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SereneSecondary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    amenities.take(2).forEach { amenity ->
                        AmenityCard(amenity = amenity, isHindi = isHindi, modifier = Modifier.weight(1f))
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    amenities.drop(2).forEach { amenity ->
                        AmenityCard(amenity = amenity, isHindi = isHindi, modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        // 6. Immediate Clinical Urgent Floor Help Action
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = SereneErrorContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onUrgentHelp() }
                    .testTag("urgent_floor_help_button")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(SereneError),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Emergency,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column {
                            Text(
                                text = if (isHindi) "तत्काल फ्लोर सहायता" else "Urgent Floor Help",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SereneOnErrorContainer
                                )
                            )
                            Text(
                                text = if (isHindi) "नजदीकी स्टेशन ३ ऑन-ड्यूटी नर्स को अलर्ट करें" else "Alert nearest Station 3 on-duty nurse",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SereneOnErrorContainer,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = if (isHindi) "कॉल करें" else "Call Now",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = SereneOnErrorContainer
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = SereneOnErrorContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AmenityCard(
    amenity: Amenity,
    isHindi: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = SereneSurfaceContainerLow,
        border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.5f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(SereneSecondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                val icon = when (amenity.iconName) {
                    "local_cafe" -> Icons.Default.LocalCafe
                    "health_and_safety" -> Icons.Default.HealthAndSafety
                    "elderly" -> Icons.Default.Elderly
                    "water_drop" -> Icons.Default.WaterDrop
                    else -> Icons.Default.Store
                }
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = SerenePrimary,
                    modifier = Modifier.size(18.dp)
                )
            }

            Text(
                text = if (isHindi) amenity.titleHi else amenity.titleEn,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = SereneOnSurface,
                    fontSize = 11.sp
                ),
                maxLines = 1
            )

            Text(
                text = if (isHindi) amenity.locationHi else amenity.locationEn,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = SereneOnSurfaceVariant,
                    fontSize = 10.sp
                ),
                maxLines = 1
            )
        }
    }
}

@Composable
fun BarcodeCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val barColor = Color(0xFF1E3A8A)

        // Pattern of bar widths and gaps
        val pattern = listOf(3, 2, 4, 2, 6, 2, 4, 1, 5, 3, 2, 7, 3, 1, 5, 3, 6, 2, 4, 3, 5, 2, 6, 1, 4, 3, 6, 2, 4, 2, 5, 3, 4, 2, 5, 2, 4, 2)
        var curX = 0f
        val scale = width / 140f

        pattern.forEachIndexed { index, w ->
            val barW = w * scale
            if (index % 2 == 0) {
                drawRect(
                    color = barColor,
                    topLeft = Offset(curX, 0f),
                    size = Size(barW, height)
                )
            }
            curX += barW
        }
    }
}

@Composable
fun HospitalFloorPlanCanvas(
    modifier: Modifier = Modifier,
    isHindi: Boolean
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        // Outer corridor floor
        drawRoundRect(
            color = Color(0xFFEFF6FF),
            topLeft = Offset(10.dp.toPx(), 10.dp.toPx()),
            size = Size(width - 20.dp.toPx(), height - 20.dp.toPx()),
            cornerRadius = CornerRadius(12.dp.toPx())
        )

        val roomFill = Color(0xFFDBEAFE)
        val roomBorder = Color(0xFF93C5FD)

        // 1. Central Pharmacy (Top Left)
        drawRoundRect(
            color = roomFill,
            topLeft = Offset(20.dp.toPx(), 18.dp.toPx()),
            size = Size(width * 0.28f, height * 0.26f),
            cornerRadius = CornerRadius(6.dp.toPx())
        )

        // 2. Station 3 Desk (Top Middle)
        drawRoundRect(
            color = roomFill,
            topLeft = Offset(width * 0.35f, 18.dp.toPx()),
            size = Size(width * 0.26f, height * 0.26f),
            cornerRadius = CornerRadius(6.dp.toPx())
        )

        // 3. Room 214 Echo Lab (Top Right - Highlighted Destination)
        drawRoundRect(
            color = Color(0xFFBFDBFE),
            topLeft = Offset(width * 0.66f, 18.dp.toPx()),
            size = Size(width * 0.28f, height * 0.32f),
            cornerRadius = CornerRadius(8.dp.toPx())
        )
        drawRoundRect(
            color = Color(0xFF2563EB),
            topLeft = Offset(width * 0.66f, 18.dp.toPx()),
            size = Size(width * 0.28f, height * 0.32f),
            cornerRadius = CornerRadius(8.dp.toPx()),
            style = Stroke(width = 2.dp.toPx())
        )

        // 4. Waiting Lounge B (Bottom Left - Start Location)
        drawRoundRect(
            color = Color(0xFFDBEAFE),
            topLeft = Offset(20.dp.toPx(), height * 0.62f),
            size = Size(width * 0.36f, height * 0.28f),
            cornerRadius = CornerRadius(6.dp.toPx())
        )

        // 5. Restrooms & Elevators Bank B
        drawRoundRect(
            color = roomFill,
            topLeft = Offset(width * 0.44f, height * 0.66f),
            size = Size(width * 0.24f, height * 0.24f),
            cornerRadius = CornerRadius(6.dp.toPx())
        )

        // 6. Meditation Room (Bottom Right)
        drawRoundRect(
            color = roomFill,
            topLeft = Offset(width * 0.72f, height * 0.66f),
            size = Size(width * 0.22f, height * 0.24f),
            cornerRadius = CornerRadius(6.dp.toPx())
        )

        // Corridor route path (Dotted & glowing from Waiting Lounge B to Room 214)
        val startX = 20.dp.toPx() + (width * 0.36f) / 2
        val startY = height * 0.62f
        val hallwayY = height * 0.48f
        val destX = width * 0.66f + (width * 0.28f) / 2
        val destY = 18.dp.toPx() + height * 0.32f

        val routePath = Path().apply {
            moveTo(startX, startY)
            lineTo(startX, hallwayY)
            lineTo(destX, hallwayY)
            lineTo(destX, destY)
        }

        // Glow
        drawPath(
            path = routePath,
            color = Color(0xFF93C5FD).copy(alpha = 0.6f),
            style = Stroke(width = 8.dp.toPx())
        )
        // Dashed path
        drawPath(
            path = routePath,
            color = Color(0xFF2563EB),
            style = Stroke(
                width = 3.5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 12f), 0f)
            )
        )

        // Start point circle (Waiting Lounge B)
        drawCircle(
            color = Color(0xFF1E3A8A),
            radius = 7.dp.toPx(),
            center = Offset(startX, startY)
        )
        drawCircle(
            color = Color(0xFFBFDBFE),
            radius = 3.dp.toPx(),
            center = Offset(startX, startY)
        )

        // Destination Pin Flag (Room 214)
        drawCircle(
            color = Color(0xFFEF4444),
            radius = 8.dp.toPx(),
            center = Offset(destX, destY)
        )
        drawCircle(
            color = Color.White,
            radius = 3.dp.toPx(),
            center = Offset(destX, destY)
        )

        // Room Labels via native canvas
        val paint = android.graphics.Paint().apply {
            color = android.graphics.Color.parseColor("#1E3A8A")
            textSize = 28f
            isFakeBoldText = true
            textAlign = android.graphics.Paint.Align.CENTER
        }
        val subPaint = android.graphics.Paint().apply {
            color = android.graphics.Color.parseColor("#475569")
            textSize = 22f
            textAlign = android.graphics.Paint.Align.CENTER
        }

        drawContext.canvas.nativeCanvas.apply {
            drawText("Pharmacy", 20.dp.toPx() + (width * 0.28f) / 2, 18.dp.toPx() + height * 0.16f, paint)
            drawText("Station 3", width * 0.35f + (width * 0.26f) / 2, 18.dp.toPx() + height * 0.16f, paint)
            drawText("Echo Lab 214", width * 0.66f + (width * 0.28f) / 2, 18.dp.toPx() + height * 0.16f, paint)
            drawText("Waiting B", startX, startY + 22.dp.toPx(), paint)
            drawText("(You are here)", startX, startY + 34.dp.toPx(), subPaint)
            drawText("Elevator", width * 0.44f + (width * 0.24f) / 2, height * 0.66f + height * 0.14f, paint)
        }
    }
}
