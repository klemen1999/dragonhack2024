/*
 * Copyright 2023 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.mediapipe.examples.poselandmarker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mediapipe.examples.poselandmarker.api.FitRepository
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.acos
import kotlin.math.sqrt

/**
 *  This ViewModel is used to store pose landmarker helper settings
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val fitRepository: FitRepository
) : ViewModel() {

    private var _model = PoseLandmarkerHelper.MODEL_POSE_LANDMARKER_FULL
    private var _delegate: Int = PoseLandmarkerHelper.DELEGATE_CPU
    private var _minPoseDetectionConfidence: Float =
        PoseLandmarkerHelper.DEFAULT_POSE_DETECTION_CONFIDENCE
    private var _minPoseTrackingConfidence: Float = PoseLandmarkerHelper
        .DEFAULT_POSE_TRACKING_CONFIDENCE
    private var _minPosePresenceConfidence: Float = PoseLandmarkerHelper
        .DEFAULT_POSE_PRESENCE_CONFIDENCE

    val currentDelegate: Int get() = _delegate
    val currentModel: Int get() = _model
    val currentMinPoseDetectionConfidence: Float
        get() =
            _minPoseDetectionConfidence
    val currentMinPoseTrackingConfidence: Float
        get() =
            _minPoseTrackingConfidence
    val currentMinPosePresenceConfidence: Float
        get() =
            _minPosePresenceConfidence

    fun setDelegate(delegate: Int) {
        _delegate = delegate
    }

    fun setMinPoseDetectionConfidence(confidence: Float) {
        _minPoseDetectionConfidence = confidence
    }

    fun setMinPoseTrackingConfidence(confidence: Float) {
        _minPoseTrackingConfidence = confidence
    }

    fun setMinPosePresenceConfidence(confidence: Float) {
        _minPosePresenceConfidence = confidence
    }

    fun setModel(model: Int) {
        _model = model
    }

    var isUp: Boolean = false
    var isFirstDetected: Boolean = false
    var counter: Float = 0f

    var userId = 0
    var challengeId = ""

    fun sendData() {
        viewModelScope.launch {
            fitRepository.putExerciseToChallenge(userId, challengeId, "1", counter.toInt())
        }
    }
    fun calculateAngleBetweenArms(
        poseLandmarkerResults: PoseLandmarkerResult,
        imageHeight: Int,
        imageWidth: Int,
    ) {
        poseLandmarkerResults.let { poseLandmarkerResult ->
            if (poseLandmarkerResults.landmarks().isEmpty() || poseLandmarkerResults.landmarks()
                    .get(0).isEmpty()
            ) {
                return
            }
            val leftShoulder = poseLandmarkerResults.landmarks().get(0).get(11)
            val leftElbow = poseLandmarkerResults.landmarks().get(0).get(13)
            val leftWrist = poseLandmarkerResults.landmarks().get(0).get(15)

            val vectorFirst =
                Pair(leftElbow.x() - leftShoulder.x(), leftElbow.y() - leftShoulder.y())
            val vectorSecond = Pair(leftElbow.x() - leftWrist.x(), leftElbow.y() - leftWrist.y())
            //calculate angle between arms
            val dotProduct =
                vectorFirst.first * vectorSecond.first + vectorFirst.second * vectorSecond.second
            val sizeFirst = sqrt((vectorFirst.first * vectorFirst.first).toDouble())
            val sizeSecond = sqrt((vectorSecond.first * vectorSecond.first).toDouble())
            val cos = dotProduct / (sizeFirst * sizeSecond)
            val angle = Math.toDegrees(acos(cos))

            println("angle: $angle, counter: $counter")

            if (angle > 140) {
                //arms are up
                if (isFirstDetected) {
                    isUp = true
                    isFirstDetected = false
                } else if (!isUp) {
                    isUp = true
                    counter += 0.5f
                }
            } else if (angle < 100) {
                if (isFirstDetected) {
                    isUp = false
                    isFirstDetected = false
                } else if (isUp) {
                    isUp = false
                    counter += 0.5f
                }
            }
        }
    }
}