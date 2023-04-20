package com.sr.slyzebradatawedgesimple.zebra.utils_zebra

import android.graphics.Bitmap
import android.graphics.Color


fun convertMmToDots(mm: Float, dpi: Int): Int {

    // convert tin inches
    val inches = mm / 25.4f
    //convert to dots
    return (inches * dpi).toInt()
}


fun convertToMonochrome(bitmap: Bitmap, threshold: Int = 128): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val monochromeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    for (y in 0 until height) {
        for (x in 0 until width) {
            val pixel = bitmap.getPixel(x, y)
            val gray = (Color.red(pixel) + Color.green(pixel) + Color.blue(pixel)) / 3
            val bwColor = if (gray < threshold) Color.BLACK else Color.WHITE
            monochromeBitmap.setPixel(x, y, bwColor)
        }
    }

    return monochromeBitmap
}