package com.google.mediapipe.examples.poselandmarker.api

import com.google.mediapipe.examples.poselandmarker.api.dto.ChallengeDto
import com.google.mediapipe.examples.poselandmarker.api.dto.ExerciseDto
import com.google.mediapipe.examples.poselandmarker.api.models.ChallengePayload
import com.google.mediapipe.examples.poselandmarker.api.models.ExercisePayload
import com.google.mediapipe.examples.poselandmarker.api.models.ExercisePayload2
import com.google.mediapipe.examples.poselandmarker.api.models.UserPayload
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @GET("challenges")
    suspend fun getChallenges(): List<ChallengeDto>

    @POST("user")
    suspend fun createUser(@Body userPayload: UserPayload)

    @PUT
    suspend fun joinChallenge(@Body challengePayload: ChallengePayload)

    @GET("challenges/")
    suspend fun getChallengesByUserId(@Query(value="userId") userId: Int): List<ChallengeDto>

    @PUT("challenge/exercise")
    suspend fun putExerciseToChallenge(
        @Body exercisePayload2: ExercisePayload2
    )

    @POST("exercise")
    suspend fun putExercise(
        @Body exercisePayload: ExercisePayload
    ): DDDD

    @GET("exercises/")
    suspend fun getExercise(@Query(value="userId") userId: Int): List<ExerciseDto>
}

data class DDDD(
    val exerciseId: String?
)