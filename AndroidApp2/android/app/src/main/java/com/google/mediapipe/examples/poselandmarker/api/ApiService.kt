package com.google.mediapipe.examples.poselandmarker.api

import com.google.mediapipe.examples.poselandmarker.api.models.Challenge
import com.google.mediapipe.examples.poselandmarker.api.models.UserPayload
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("challenges")
    suspend fun getChallenges(): List<Challenge>

    @POST("user")
    suspend fun createUser(@Body userPayload: UserPayload)
}