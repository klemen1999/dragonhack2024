package com.google.mediapipe.examples.poselandmarker.api.dto

data class ChallengeDto(
    val _id: String,
    val participants: List<String>?,
    val exerciseType: String?,
    val exercises: List<String>?,
    val startTime: String?,
    val endTime: String?,
    val recurrence: Int?,
    val description: String?
)