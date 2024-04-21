package com.google.mediapipe.examples.poselandmarker.api

import com.google.mediapipe.examples.poselandmarker.api.dto.ChallengeDto
import com.google.mediapipe.examples.poselandmarker.api.models.UserPayload
import javax.inject.Inject

class FitRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getChallenges(): List<ChallengeDto> {
        return try {
            return apiService.getChallenges()
        } catch (e: Exception) {
            println(e.message)
            emptyList()
        }

    }

    suspend fun createUser(userId: Int, name: String) {
        try {
            apiService.createUser(UserPayload(userId, name))
        } catch (e: Exception) {
            // Handle error
            println(e.message)
        }

    }
}