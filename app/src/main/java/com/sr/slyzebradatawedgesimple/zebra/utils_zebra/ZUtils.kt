package com.sr.slyzebradatawedgesimple.zebra.utils_zebra



fun convertMmToDots(mm: Float, dpi: Int): Int {

    // convert tin inches
    val inches = mm / 25.4f
    //convert to dots
    return (inches * dpi).toInt()
}