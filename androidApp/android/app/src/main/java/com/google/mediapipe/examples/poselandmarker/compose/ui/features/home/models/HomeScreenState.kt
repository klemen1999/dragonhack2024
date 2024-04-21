package com.google.mediapipe.examples.poselandmarker.compose.ui.features.home.models

import com.google.mediapipe.examples.poselandmarker.api.dto.ChallengeDto
import com.google.mediapipe.examples.poselandmarker.api.dto.ExerciseDto

data class HomeData(
    val challenges: List<ChallengeDto>,
    val myChallenges: List<ChallengeDto> = emptyList(),
    val myExercises: List<ExerciseDto> = emptyList()
)


sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Loaded(val homeData: HomeData) : HomeScreenState()
}