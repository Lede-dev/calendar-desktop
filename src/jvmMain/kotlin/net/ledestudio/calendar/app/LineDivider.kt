package net.ledestudio.calendar.app

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object LineDivider {

    @Composable
    private fun VerticalDivider(color: Color = Color.White, thickness: Dp = 1.dp) {
        Divider(
            color = color,
            modifier = Modifier.fillMaxHeight().width(thickness)
        )
    }

    @Composable
    private fun HorizontalDivider(color: Color = Color.White, thickness: Dp = 1.dp) {
        Divider(
            color = color,
            thickness = thickness
        )
    }

}