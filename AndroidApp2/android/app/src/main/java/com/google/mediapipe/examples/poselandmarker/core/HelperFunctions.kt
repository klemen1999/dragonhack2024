package com.google.mediapipe.examples.poselandmarker.core

import java.time.LocalDateTime

fun formatDateToString(date: LocalDateTime): String {
    val year = date.year
    val month = date.monthValue
    val day = date.dayOfMonth
    val hour = date.hour
    val minute = date.minute
    // Format: 31/12/2021 23:59
    return "${String.format("%02d", day)}/${String.format("%02d", month)}/$year $hour:$minute"
}