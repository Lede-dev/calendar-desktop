package net.ledestudio.calendar.utils

import androidx.compose.ui.graphics.Color

object Colors {

    fun fromHex(hexString: String): Color {
        return Color(("ff" + hexString.removePrefix("#").lowercase()).toLong(16))
    }

}