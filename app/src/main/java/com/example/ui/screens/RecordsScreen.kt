package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DiagnosticRecord
import com.example.ui.theme.*

@Composable
fun RecordsScreen(
    records: List<DiagnosticRecord>,
    isHindi: Boolean,
    abhaId: String
) {
    var isDownloaded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("records_screen")
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ABHA Health Locker Header Banner
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SerenePrimary),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth().testTag("abha_locker_card")
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
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.HealthAndSafety,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = if (isHindi) "आयुष्मान भारत डिजिटल हेल्थ रिकॉर्ड" else "ABHA Connected Health Vault",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "ABDM Certified • ABHA #$abhaId",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.8f),
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(SereneSuccessContainer)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "Active Sync",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SereneOnSuccess,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Text(
                        text = if (isHindi)
                            "सभी ओपीडी टेस्ट, लैब परिणाम और डॉक्टर के पर्चे सुरक्षित रूप से डिजिटल लॉकर में संग्रहित हैं।"
                        else
                            "Automated clinical data repository. Laboratory samples, vitals, and physician notes are directly synchronised with Dr. Ananya Sen.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                    )

                    Button(
                        onClick = { isDownloaded = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().testTag("download_records_summary_button")
                    ) {
                        Icon(
                            imageVector = if (isDownloaded) Icons.Default.CheckCircle else Icons.Default.Download,
                            contentDescription = null,
                            tint = if (isDownloaded) SereneSuccess else SerenePrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isDownloaded)
                                (if (isHindi) "पीडीएफ डाउनलोड हो गया" else "Summary Saved to Device")
                            else
                                (if (isHindi) "पूरा ओपीडी सारांश डाउनलोड करें (PDF)" else "Download Clinical Visit Summary (PDF)"),
                            color = SerenePrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // ECG Waveform Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth().testTag("ecg_waveform_card")
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
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MonitorHeart,
                                contentDescription = null,
                                tint = SereneSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = if (isHindi) "रेस्टिंग ईसीजी ट्रेस (लीड II)" else "12-Lead ECG Waveform (Lead II)",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SerenePrimary
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(SereneSuccessContainer)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = if (isHindi) "सामान्य लय (६८ bpm)" else "Normal Sinus (68 bpm)",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SereneOnSuccess,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    // Authentic ECG Waveform Canvas
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(95.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SereneSurfaceContainerLow)
                            .border(1.dp, SereneSecondaryContainer.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                    ) {
                        EcgCanvas(modifier = Modifier.fillMaxSize())
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isHindi) "डॉक्टर सत्यापन: डॉ. अनन्या सेन द्वारा स्वीकृत" else "Verified by Dr. Ananya Sen, MD DM",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SereneOnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                        Text(
                            text = "Recorded: 9:15 AM",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SereneSecondary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }

        // Section Title: Clinical Tests & Reports
        item {
            Text(
                text = if (isHindi) "आज की जांच और रिपोर्ट" else "Today's Diagnostic Reports",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = SerenePrimary
                ),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Diagnostic Records List
        items(records) { record ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.8f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isHindi) record.titleHi else record.titleEn,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SerenePrimary,
                                    fontSize = 14.sp
                                )
                            )
                            Text(
                                text = "${record.department} • ${record.time}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SereneOnSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(if (record.isCompleted) SereneSuccessContainer else SereneSecondaryContainer)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = if (record.isCompleted)
                                    (if (isHindi) "सत्यापित ✓" else "Completed ✓")
                                else
                                    (if (isHindi) "प्रक्रियाधीन" else "Processing"),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (record.isCompleted) SereneOnSuccess else SerenePrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }

                    Text(
                        text = record.summary,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SereneOnSurface,
                            fontSize = 12.sp
                        )
                    )

                    // Metrics pills
                    if (record.metrics.isNotEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            record.metrics.forEach { (label, value) ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = SereneSurfaceContainerLow,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.5f)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = 9.sp,
                                                color = SereneOnSurfaceVariant
                                            )
                                        )
                                        Text(
                                            text = value,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontSize = 11.sp,
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
            }
        }
    }
}

@Composable
fun EcgCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val midY = height / 2

        // Grid lines (subtle medical ECG graph grid)
        val gridColor = Color(0xFFDBEAFE).copy(alpha = 0.7f)
        val stepX = 20.dp.toPx()
        val stepY = 20.dp.toPx()

        var x = 0f
        while (x < width) {
            drawLine(
                color = gridColor,
                start = Offset(x, 0f),
                end = Offset(x, height),
                strokeWidth = 0.8f
            )
            x += stepX
        }

        var y = 0f
        while (y < height) {
            drawLine(
                color = gridColor,
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 0.8f
            )
            y += stepY
        }

        // Draw rhythmic ECG pulse across canvas
        val path = Path()
        path.moveTo(0f, midY)

        val cycleWidth = width / 3.2f
        var currentX = 0f

        while (currentX < width) {
            // Baseline
            path.lineTo(currentX + cycleWidth * 0.15f, midY)
            // P wave
            path.cubicTo(
                currentX + cycleWidth * 0.20f, midY - height * 0.12f,
                currentX + cycleWidth * 0.25f, midY - height * 0.12f,
                currentX + cycleWidth * 0.30f, midY
            )
            // PR segment
            path.lineTo(currentX + cycleWidth * 0.38f, midY)
            // Q wave
            path.lineTo(currentX + cycleWidth * 0.40f, midY + height * 0.08f)
            // R peak
            path.lineTo(currentX + cycleWidth * 0.44f, midY - height * 0.42f)
            // S wave
            path.lineTo(currentX + cycleWidth * 0.48f, midY + height * 0.18f)
            // ST segment
            path.lineTo(currentX + cycleWidth * 0.55f, midY)
            // T wave
            path.cubicTo(
                currentX + cycleWidth * 0.62f, midY - height * 0.18f,
                currentX + cycleWidth * 0.72f, midY - height * 0.18f,
                currentX + cycleWidth * 0.80f, midY
            )
            // Baseline to next cycle
            path.lineTo(currentX + cycleWidth, midY)
            currentX += cycleWidth
        }

        drawPath(
            path = path,
            color = Color(0xFF2563EB),
            style = Stroke(
                width = 2.4.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}
