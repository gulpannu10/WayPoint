package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.PatientProfile
import com.example.ui.theme.*

@Composable
fun WaypointTopBar(
    profile: PatientProfile,
    isHindi: Boolean,
    currentTabTitle: String,
    onToggleLanguage: () -> Unit,
    onEmergencyClick: () -> Unit
) {
    // Pulsing animation for token active status
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = Color.White.copy(alpha = 0.95f),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            // Row 1: Hospital Pavilion Brand, Language Toggle & Emergency Help
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
                            .clip(RoundedCornerShape(10.dp))
                            .background(SereneSecondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalHospital,
                            contentDescription = "WayPoint Hospital",
                            tint = SerenePrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "WayPoint",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SerenePrimary
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SereneSecondaryContainer)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (isHindi) "अपोलो पवेलियन" else "Apollo Pavilion",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SereneSecondary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                        Text(
                            text = if (isHindi) "संबद्ध ओपीडी यात्रा • $currentTabTitle" else "Connected OPD Journey • $currentTabTitle",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SereneOnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                // Action buttons: Language toggle + Help button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Language Switcher
                    Surface(
                        modifier = Modifier
                            .testTag("language_toggle_button")
                            .clip(RoundedCornerShape(9999.dp))
                            .border(1.dp, SereneSecondaryContainer, RoundedCornerShape(9999.dp))
                            .clickable { onToggleLanguage() },
                        color = SereneSurfaceContainerLow
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Translate,
                                contentDescription = "Switch language",
                                tint = SereneSecondary,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = if (isHindi) "English" else "A / अ हिन्दी",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SereneSecondary
                                )
                            )
                        }
                    }

                    // Emergency Help Button
                    Surface(
                        modifier = Modifier
                            .testTag("emergency_help_button")
                            .clip(RoundedCornerShape(9999.dp))
                            .clickable { onEmergencyClick() },
                        color = SereneErrorContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Emergency,
                                contentDescription = "Emergency Assistance",
                                tint = SereneOnErrorContainer,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = if (isHindi) "सहायता" else "Help",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SereneOnErrorContainer
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row 2: Patient Info Strip + Active Token Pill
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = SereneSurfaceContainerLow,
                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.6f))
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
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Avatar
                        AsyncImage(
                            model = profile.avatarUrl,
                            contentDescription = profile.name,
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, SerenePrimaryContainer, CircleShape)
                        )

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = profile.name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = SerenePrimary,
                                        fontSize = 13.sp
                                    )
                                )
                                Text(
                                    text = "(${profile.ageGender})",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = SereneOnSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                            Text(
                                text = "ABHA: #${profile.abhaId}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SereneSecondary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }

                    // Token Pill
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9999.dp))
                            .background(SereneSecondaryContainer)
                            .border(1.dp, SerenePrimaryContainer.copy(alpha = 0.4f), RoundedCornerShape(9999.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(SereneSecondary.copy(alpha = pulseAlpha))
                        )
                        Text(
                            text = if (isHindi) "टोकन: ${profile.opdToken}" else "OPD Token: ${profile.opdToken}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = SerenePrimary
                            )
                        )
                    }
                }
            }
        }
    }
}
