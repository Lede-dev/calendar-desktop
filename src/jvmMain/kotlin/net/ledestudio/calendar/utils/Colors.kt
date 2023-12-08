package net.ledestudio.calendar.utils

import androidx.compose.ui.graphics.Color

object Colors {

    val background = fromHex("#333333")
    val text = fromHex("#CCCCCC")
    val icon = fromHex("#CCCCCC")
    val highlight = fromHex("#2ecc71")

    fun fromHex(hexString: String): Color {
        return Color(("ff" + hexString.removePrefix("#").lowercase()).toLong(16))
    }

}