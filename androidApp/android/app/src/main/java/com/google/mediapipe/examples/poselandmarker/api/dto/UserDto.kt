package com.google.mediapipe.examples.poselandmarker.api.dto

data class UserDto(
    val _id: String,
    val userId: Int?,
    val name: String?,
    val activeChallenges: List<String>?,
    val todoChallenges: List<String>?,
    val bestscores: List<String>?,
)