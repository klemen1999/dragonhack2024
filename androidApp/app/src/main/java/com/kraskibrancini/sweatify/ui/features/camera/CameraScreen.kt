package com.kraskibrancini.sweatify.ui.features.camera

import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import ai.onnxruntime.extensions.OrtxPackage
import android.content.Context
import android.graphics.Bitmap
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.kraskibrancini.sweatify.R
import com.kraskibrancini.sweatify.detector.PoseDetector
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    viewModel: CameraViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
               Text(text = "Record Your Exercise")
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Start Recording")
                }
            }
        }
    ) { padVal ->
        // Camera view
        CameraContent(modifier = Modifier.padding(padVal))
    }
}

@androidx.annotation.OptIn(ExperimentalGetImage::class) @Composable
fun CameraContent(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current


   // val imageAnalysis = ImageAnalysis.Builder()
   //     .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
    //    .build()

    val executor = remember { Executors.newSingleThreadExecutor() }

    val ortEnv: OrtEnvironment = OrtEnvironment.getEnvironment()
    val sessionOptions: OrtSession.SessionOptions = OrtSession.SessionOptions()
    sessionOptions.registerCustomOpLibrary(OrtxPackage.getLibraryPath())
    val ortSession = ortEnv.createSession(readModel(context), sessionOptions)

    val poseDetector = PoseDetector(ortSession=ortSession, ortEnv=ortEnv, confThreshold = 0.5f)
    val analizer = PoseDetector(ortSession=ortSession, ortEnv=ortEnv, confThreshold = 0.5f)
    var keypointsScaled: List<Pair<Int,Int>> = emptyList()
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            setImageAnalysisAnalyzer(executor, analizer)
            /*setImageAnalysisAnalyzer(executor) { imageProxy ->
                keypointsScaled = poseDetector.detect(imageProxy)
            }*/
        }
    }
    val keypoints = analizer.keypointsFlow.collectAsState()

    CameraPreview(controller = controller,
        modifier = modifier.fillMaxSize(),
        keypoints = keypoints.value
    )
}

private fun readModel(context:Context): ByteArray {
    val modelID = R.raw.hrnet_coco_w48_384x288
    val resources = context.resources
    return resources.openRawResource(modelID).readBytes()
}

@Preview
@Composable
fun CameraScreenPreview() {

}