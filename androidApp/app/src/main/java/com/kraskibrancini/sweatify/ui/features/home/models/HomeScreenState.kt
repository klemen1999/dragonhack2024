package com.kraskibrancini.sweatify.ui.features.home.models

import com.kraskibrancini.sweatify.models.Challenge

data class HomeData(
    val challenges: List<Challenge>
)


sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Loaded(val homeData: HomeData) : HomeScreenState()
}

