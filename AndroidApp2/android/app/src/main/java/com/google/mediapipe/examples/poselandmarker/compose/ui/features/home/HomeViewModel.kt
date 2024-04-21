package com.google.mediapipe.examples.poselandmarker.compose.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mediapipe.examples.poselandmarker.api.FitRepository
import com.google.mediapipe.examples.poselandmarker.api.dto.ChallengeDto
import com.google.mediapipe.examples.poselandmarker.compose.ui.features.home.models.HomeData
import com.google.mediapipe.examples.poselandmarker.compose.ui.features.home.models.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fitRepository: FitRepository
) : ViewModel() {

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state: StateFlow<HomeScreenState> = _state

     fun getChallenges() {
        //TODO call API
        viewModelScope.launch {
            val challenges = fitRepository.getChallenges()
            _state.value = HomeScreenState.Loaded(
                homeData = HomeData(
                    challenges = challenges
                )
            )
        }
    }

    fun joinChallenge(challengeId: String) {
        //TODO call API

    }
}