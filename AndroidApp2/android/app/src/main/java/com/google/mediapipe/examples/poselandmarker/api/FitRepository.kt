package com.google.mediapipe.examples.poselandmarker.api

import com.google.mediapipe.examples.poselandmarker.api.models.Challenge
import com.google.mediapipe.examples.poselandmarker.api.models.UserPayload
import javax.inject.Inject

class FitRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getChallenges(): List<Challenge> {
        return try {
            apiService.getChallenges()
        } catch (e: Exception) {
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