package com.google.mediapipe.examples.poselandmarker.api.models

import java.time.LocalDateTime

data class Challenge(
    val id: Int,
    val title: String,
    val description: String,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val participants: List<String>,
    val isJoined: Boolean = false
)