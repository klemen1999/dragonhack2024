package com.google.mediapipe.examples.poselandmarker.compose.ui.features.home.models

import com.google.mediapipe.examples.poselandmarker.api.models.Challenge

data class HomeData(
    val challenges: List<Challenge>,
)


sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Loaded(val homeData: HomeData) : HomeScreenState()
}