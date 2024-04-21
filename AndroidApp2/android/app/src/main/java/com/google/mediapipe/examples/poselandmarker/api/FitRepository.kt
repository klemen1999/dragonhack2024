package com.google.mediapipe.examples.poselandmarker.api

import com.google.mediapipe.examples.poselandmarker.api.dto.ChallengeDto
import com.google.mediapipe.examples.poselandmarker.api.dto.ExerciseDto
import com.google.mediapipe.examples.poselandmarker.api.models.ChallengePayload
import com.google.mediapipe.examples.poselandmarker.api.models.ExercisePayload
import com.google.mediapipe.examples.poselandmarker.api.models.ExercisePayload2
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

    suspend fun joinChallenge(userId: Int, challengeId: String) {
        try {
            apiService.joinChallenge(ChallengePayload(userId, challengeId))
        } catch (e: Exception) {
            // Handle error
            println(e.message)
        }
    }

    suspend fun getChallengesByUserId(userId: Int): List<ChallengeDto> {
        return try {
            return apiService.getChallengesByUserId(userId)
        } catch (e: Exception) {
            println(e.message)
            emptyList()
        }
    }

    suspend fun putExerciseToChallenge(userId: Int, challengeId: String, exerciseId: String, reps: Int) {
        try {
            val exercise__Id = apiService.putExercise(ExercisePayload(userId = userId, reps = reps))
            apiService.putExerciseToChallenge(ExercisePayload2(challengeId, exercise__Id.exerciseId ?: "", userId))
        } catch (e: Exception) {
            // Handle error
            println(e.message)
        }
    }

    suspend fun getExercise(userId: Int): List<ExerciseDto> {
        return try {
            apiService.getExercise(userId)
        } catch (e: Exception) {
            // Handle error
            println(e.message)
            emptyList()
        }
    }
}