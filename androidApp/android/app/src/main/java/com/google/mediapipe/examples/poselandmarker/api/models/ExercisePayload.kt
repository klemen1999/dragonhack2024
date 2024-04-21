package com.google.mediapipe.examples.poselandmarker.api.models

data class ExercisePayload(
    val type: String = "pushup",
    val userId: Int,
    val duration: Int = 1,
    val reps: Int
)