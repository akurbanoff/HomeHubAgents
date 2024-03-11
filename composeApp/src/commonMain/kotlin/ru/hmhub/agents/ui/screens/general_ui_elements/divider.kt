package ru.hmhub.agents.ui.screens.general_ui_elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ElementDivider(){
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(0.9f),
            thickness = 2.dp,
            color = Color.Gray
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(0.7f),
            thickness = 2.dp,
            color = Color.Black
        )
    }
}