package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class NavItem(
    val titleEn: String,
    val titleHi: String,
    val icon: ImageVector,
    val testTag: String
)

@Composable
fun WaypointBottomNav(
    selectedTab: Int,
    isHindi: Boolean,
    onTabSelected: (Int) -> Unit
) {
    val items = listOf(
        NavItem(
            titleEn = "Journey",
            titleHi = "यात्रा",
            icon = Icons.Default.Route,
            testTag = "tab_journey"
        ),
        NavItem(
            titleEn = "Records",
            titleHi = "रिकॉर्ड्स",
            icon = Icons.Default.AssignmentTurnedIn,
            testTag = "tab_records"
        ),
        NavItem(
            titleEn = "Care AI",
            titleHi = "केयर AI",
            icon = Icons.Default.Psychology,
            testTag = "tab_care_ai"
        ),
        NavItem(
            titleEn = "Visit Guide",
            titleHi = "विज़िट गाइड",
            icon = Icons.Default.Explore,
            testTag = "tab_visit_guide"
        )
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = Color.White.copy(alpha = 0.95f),
        shadowElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, SereneSecondaryContainer.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedTab == index
                val label = if (isHindi) item.titleHi else item.titleEn

                Column(
                    modifier = Modifier
                        .testTag(item.testTag)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onTabSelected(index) }
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) SereneSecondaryContainer else Color.Transparent)
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = label,
                            tint = if (isSelected) SerenePrimary else SereneOnSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) SerenePrimary else SereneOnSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}
