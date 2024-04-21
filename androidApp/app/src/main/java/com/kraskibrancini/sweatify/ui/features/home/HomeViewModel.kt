package com.kraskibrancini.sweatify.ui.features.home

import androidx.lifecycle.ViewModel
import com.kraskibrancini.sweatify.models.Challenge
import com.kraskibrancini.sweatify.ui.features.home.models.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state = _state.asStateFlow()

    fun getChallenges(): List<Challenge> {
        //TODO call API
        return listOf(
            Challenge(
                id = 1,
                title = "Sklece delamo skupaj!",
                description = "Naredi 10 sklec vsak dan cel teden",
                start = LocalDateTime.now(),
                end = LocalDateTime.now().plusDays(7),
                participants = listOf("Aljosa Koren", "Klemen Skrlj"),
                isJoined = true
            ),
            Challenge(
                id = 2,
                title = "Počepi sprint",
                description = "Kolko počepov zmoreš v 1 minuti?",
                start = LocalDateTime.now(),
                end = LocalDateTime.now().plusDays(7),
                participants = listOf("User 1"),
                isJoined = false
            ),
            Challenge(
                id = 3,
                title = "Sklece sprint",
                description = "Kolko sklec zmoreš v 1 minuti?",
                start = LocalDateTime.now(),
                end = LocalDateTime.now().plusDays(7),
                participants = listOf("User 1", "User 2", "User 3")
            )
        )
    }

    fun joinChallenge(challengeId: Int) {
        //TODO call API

    }
}