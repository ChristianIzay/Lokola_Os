package com.muana.lokola.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CongoInfoWidget() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF007FFF).copy(alpha = 0.2f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Météo Kinshasa (Simulée pour l'instant)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = "Météo",
                    tint = Color(0xFFF7D618),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Kinshasa", fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
                    Text("28°C", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            // Actualité rapide
            Column(horizontalAlignment = Alignment.End) {
                Text("Rumba au patrimoine", fontSize = 11.sp, color = Color(0xFFF7D618), fontWeight = FontWeight.Medium)
                Text("UNESCO • Culture", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
            }
        }
    }
}
