package com.google.mediapipe.examples.poselandmarker.compose.ui.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mediapipe.examples.poselandmarker.api.FitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val fitRepository: FitRepository
) : ViewModel() {

    fun login(userId: Int, name: String) {
        viewModelScope.launch {
            try {
                fitRepository.createUser(userId, name)
            } catch (e: Exception) {
            }
        }
    }
}