package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.NurseNavigator
import com.example.ui.theme.*

@Composable
fun EmergencyHelpDialog(
    isHindi: Boolean,
    onDismiss: () -> Unit,
    onAlertFloorStaff: () -> Unit
) {
    var staffAlerted by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            shadowElevation = 12.dp,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(SereneErrorContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Emergency,
                        contentDescription = "Emergency Help",
                        tint = SereneError,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = if (isHindi) "तत्काल फ्लोर सहायता" else "Urgent Floor Assistance",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = SereneOnSurface
                    )
                )

                Text(
                    text = if (staffAlerted) {
                        if (isHindi)
                            "स्टेशन ३ ऑन-ड्यूटी नर्स टीम को अलर्ट भेजा गया है। कृपया प्रतीक्षा करें, सहायक आ रहे हैं।"
                        else
                            "Station 3 floor nursing team has been alerted with your current location. Please remain seated; staff is approaching."
                    } else {
                        if (isHindi)
                            "यदि आपको सांस लेने में तकलीफ, चक्कर या असहजता महसूस हो रही है, तो तुरंत स्टेशन ३ नर्सिंग टीम को बुलाएं।"
                        else
                            "If you or your patient are experiencing acute discomfort, dizziness, or urgent distress, alert the nearest floor nurse immediately."
                    },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = SereneOnSurfaceVariant,
                        fontSize = 13.sp
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (staffAlerted) {
                    Surface(
                        color = SereneSuccessContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SereneSuccess
                            )
                            Text(
                                text = if (isHindi) "सहायक डिस्पैच हुआ • ईटीए २ मिनट" else "Nurse Dispatched • ETA ~2 mins",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SereneOnSuccess
                                )
                            )
                        }
                    }
                } else {
                    Button(
                        onClick = {
                            staffAlerted = true
                            onAlertFloorStaff()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SereneError),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().testTag("alert_nurse_station_button")
                    ) {
                        Icon(imageVector = Icons.Default.NotificationsActive, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isHindi) "नर्स स्टेशन अलर्ट भेजें" else "Alert On-Duty Nurse Station",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                OutlinedButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (isHindi) "बंद करें" else "Close")
                }
            }
        }
    }
}

@Composable
fun SahayakRequestDialog(
    isHindi: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            shadowElevation = 10.dp,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(SereneSecondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Accessible,
                        contentDescription = "Wheelchair / Sahayak",
                        tint = SerenePrimary,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Text(
                    text = if (isHindi) "सहायक / व्हीलचेयर अनुरोध" else "Request Sahayak / Wheelchair",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = SereneOnSurface
                    )
                )

                Text(
                    text = if (isHindi)
                        "एक प्रशिक्षित ओपीडी सहायक (हेल्पडेस्क २बी) व्हीलचेयर के साथ कमरा २१४ / वेटिंग लाउंज बी में सहायता के लिए आ जाएगा।"
                    else
                        "A trained hospital Sahayak stationed at Helpdesk 2B will arrive with a comfortable transit wheelchair to guide Priya Sharma to Room 214.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = SereneOnSurfaceVariant)
                )

                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(containerColor = SerenePrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().testTag("confirm_sahayak_request")
                ) {
                    Text(text = if (isHindi) "सहायक बुलाएं (~3 मिनट)" else "Dispatch Sahayak (~3 mins)")
                }

                TextButton(onClick = onDismiss) {
                    Text(text = if (isHindi) "रद्द करें" else "Cancel")
                }
            }
        }
    }
}

@Composable
fun AddAttendantDialog(
    isHindi: Boolean,
    onDismiss: () -> Unit,
    onAdd: (name: String, phone: String, relation: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var relation by remember { mutableStateOf(if (isHindi) "तीमारदार" else "Attendant") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            shadowElevation = 10.dp,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (isHindi) "अटेंडेंट / WhatsApp नंबर जोड़ें" else "Add Family Attendant",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = SereneOnSurface
                    )
                )

                Text(
                    text = if (isHindi)
                        "ओपीडी प्रगति, टेस्ट रिपोर्ट और कमरे के निर्देशों के लाइव अपडेट परिवार के सदस्य को भेजे जाएंगे।"
                    else
                        "Real-time WhatsApp updates regarding doctor calls, ultrasound token status, and pharmacy alerts will be sent automatically.",
                    style = MaterialTheme.typography.bodySmall.copy(color = SereneOnSurfaceVariant)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(if (isHindi) "नाम (Name)" else "Attendant Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text(if (isHindi) "मोबाइल नंबर (WhatsApp)" else "WhatsApp Mobile (+91)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = relation,
                    onValueChange = { relation = it },
                    label = { Text(if (isHindi) "रिश्ता (Relation)" else "Relationship (e.g. Son/Spouse)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(if (isHindi) "रद्द करें" else "Cancel")
                    }
                    Button(
                        onClick = {
                            if (name.isNotBlank() && phone.isNotBlank()) {
                                onAdd(name, phone, relation)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SerenePrimary),
                        shape = RoundedCornerShape(10.dp),
                        enabled = name.isNotBlank() && phone.isNotBlank(),
                        modifier = Modifier.testTag("save_attendant_button")
                    ) {
                        Text(if (isHindi) "सुरक्षित करें" else "Save & Sync")
                    }
                }
            }
        }
    }
}

@Composable
fun NurseCallDialog(
    nurse: NurseNavigator,
    isHindi: Boolean,
    onDismiss: () -> Unit
) {
    var callConnecting by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            shadowElevation = 10.dp,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(SereneSecondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Nurse Call",
                        tint = SerenePrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = if (isHindi) "${nurse.name} से संपर्क करें" else "Connect with ${nurse.name}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = SereneOnSurface
                    )
                )

                Text(
                    text = "${nurse.title} • ${nurse.supervisingDoctor}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = SerenePrimary,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = if (isHindi)
                        "सिस्टर लक्ष्मी वर्तमान में ओपीडी विंग सी में उपस्थित हैं और आपके टेस्ट या डॉक्टर की प्रतीक्षा में मार्गदर्शन कर सकती हैं।"
                    else
                        "Sister Lakshmi is on duty in OPD Wing C and can answer clinical prep questions or coordinate with Dr. Ananya Sen.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = SereneOnSurfaceVariant)
                )

                if (callConnecting) {
                    Surface(
                        color = SereneSecondaryContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = SerenePrimary,
                                strokeWidth = 2.dp
                            )
                            Text(
                                text = if (isHindi) "नर्स डेस्क २बी से कनेक्ट हो रहा है..." else "Connecting to Desk 2B intercom...",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SerenePrimary
                                )
                            )
                        }
                    }
                } else {
                    Button(
                        onClick = { callConnecting = true },
                        colors = ButtonDefaults.buttonColors(containerColor = SerenePrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().testTag("start_nurse_call_button")
                    ) {
                        Icon(imageVector = Icons.Default.Call, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = if (isHindi) "कॉल शुरू करें" else "Start Audio Call")
                    }
                }

                OutlinedButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (isHindi) "बंद करें" else "Close")
                }
            }
        }
    }
}
