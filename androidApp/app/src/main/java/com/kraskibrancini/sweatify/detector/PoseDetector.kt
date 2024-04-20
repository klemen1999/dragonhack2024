package com.kraskibrancini.sweatify.detector

import ai.onnxruntime.OnnxJavaType
import ai.onnxruntime.OrtSession
import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.SystemClock
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.kraskibrancini.sweatify.core.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.InputStream
import java.nio.ByteBuffer
import java.util.*

class PoseDetector(
    val ortSession: OrtSession,
    val ortEnv: OrtEnvironment,
    val confThreshold: Float
): ImageAnalysis.Analyzer {

    val keypointsFlow = MutableStateFlow<List<Pair<Int, Int>>>(emptyList())

    fun detect(image: ImageProxy): List<Pair<Int,Int>> {
        val imgBitmap = image.toBitmap()
        val originalWidth = image.width
        val originalHeight = image.height
        val rawBitmap = imgBitmap.let { Bitmap.createScaledBitmap(it, Constants.modelWidth, Constants.modelHeight, false) }
        val bitmap = rawBitmap.rotate(image.imageInfo.rotationDegrees.toFloat())

        val imgData = preProcess(bitmap)
        val inputName = ortSession?.inputNames?.iterator()?.next()
        val shape = longArrayOf(1, 3, Constants.modelWidth.toLong(), Constants.modelHeight.toLong())
        val env = OrtEnvironment.getEnvironment()
        var keypointsScaled = emptyList<Pair<Int, Int>>()
        env.use {
            val tensor = OnnxTensor.createTensor(env, imgData, shape)
            tensor.use {
                val output = ortSession.run(Collections.singletonMap(inputName, tensor))
                output.use{
                    val rawOutput = ((output?.get(0)?.value) as Array<Array<Array<FloatArray>>>)[0]
                    val keypoints = postprocessOutput(rawOutput)
                    keypointsScaled = keypoints.map { pair ->
                        val xScaled = pair.first * originalWidth / Constants.modelWidth
                        val yScaled = pair.second * originalHeight / Constants.modelHeight
                        return@map Pair(xScaled, yScaled)
                    }
                }
            }
        }

        return keypointsScaled
    }

    // Rotate the image of the input bitmap
    private fun Bitmap.rotate(degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

    private fun postprocessOutput(input:Array<Array<FloatArray>>): List<Pair<Int,Int>>{
        // Array to store the max values from each slice
        val maxValues = mutableListOf<Float>()
        val maxIndices = mutableListOf<Pair<Int, Int>>()
        // Process each 2D slice
        input.forEachIndexed { index, slice ->
            var maxVal = Float.MIN_VALUE
            var maxCoord = Pair(-1, -1)
            slice.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, value ->
                    if (value > maxVal) {
                        maxVal = value
                        maxCoord = Pair(rowIndex, colIndex)
                    }
                }
            }
            if (maxVal < confThreshold){
                maxCoord = Pair(-1,-1)
            }
            maxValues.add(maxVal)
            maxIndices.add(maxCoord)
        }
        return maxIndices
    }

    companion object {
        fun detect(imageProxy: ImageProxy) {
        }
    }

    override fun analyze(image: ImageProxy) {
        val imgBitmap = image.toBitmap()
        val originalWidth = image.width
        val originalHeight = image.height
        val rawBitmap = imgBitmap.let { Bitmap.createScaledBitmap(it, Constants.modelWidth, Constants.modelHeight, false) }
        val bitmap = rawBitmap.rotate(image.imageInfo.rotationDegrees.toFloat())

        val imgData = preProcess(bitmap)
        val inputName = ortSession?.inputNames?.iterator()?.next()
        val shape = longArrayOf(1, 3, Constants.modelWidth.toLong(), Constants.modelHeight.toLong())
        val env = OrtEnvironment.getEnvironment()
        var keypointsScaled = emptyList<Pair<Int, Int>>()
        env.use {
            val tensor = OnnxTensor.createTensor(env, imgData, shape)
            tensor.use {
                val output = ortSession.run(Collections.singletonMap(inputName, tensor))
                output.use{
                    val rawOutput = ((output?.get(0)?.value) as Array<Array<Array<FloatArray>>>)[0]
                    val keypoints = postprocessOutput(rawOutput)
                    keypointsScaled = keypoints.map { pair ->
                        val xScaled = pair.first * originalWidth / Constants.modelWidth
                        val yScaled = pair.second * originalHeight / Constants.modelHeight
                        return@map Pair(xScaled, yScaled)
                    }
                }
            }
        }
        println(keypointsScaled)
        keypointsFlow.value = keypointsScaled
        image.close()
    }
}