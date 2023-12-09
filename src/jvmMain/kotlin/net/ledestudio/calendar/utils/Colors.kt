package net.ledestudio.calendar.utils

import androidx.compose.ui.graphics.Color

object Colors {

    val navBackground = fromHex("#333333")
    val navIcon = fromHex("#00FF00")

    val calendarBackground = fromHex("#222222")
    val calendarContent = fromHex("#333333")
    val calendarSepLine = fromHex("#555555")
    val calendarContentText = fromHex("#CCCCCC")
    val calendarTitle = fromHex("#00FF00")


    val text = fromHex("#CCCCCC")
    val icon = fromHex("#CCCCCC")
    val highlight = fromHex("#2ecc71")

    val transparent = Color(0, 0, 0, 0)

    fun fromHex(hexString: String): Color {
        return Color(("ff" + hexString.removePrefix("#").lowercase()).toLong(16))
    }

    fun fromHexAlpha(hexString: String): Color {
        return Color(hexString.removePrefix("#").lowercase().toLong(16))
    }

}