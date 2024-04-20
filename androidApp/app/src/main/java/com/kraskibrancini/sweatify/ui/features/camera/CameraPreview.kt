package com.kraskibrancini.sweatify.ui.features.camera

import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier,
    keypoints: List<Pair<Int,Int>>
) {

    val cameraPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    if (cameraPermissionState.status.isGranted) {
        val lifecycleOwner = LocalLifecycleOwner.current
        Box(modifier = Modifier.fillMaxSize()){
            AndroidView(factory = { ctx ->
                PreviewView(ctx).apply {
                    this.controller = controller
                    controller.bindToLifecycle(lifecycleOwner)
                }
            }, modifier =  Modifier.fillMaxSize())
            Canvas(modifier =  Modifier.fillMaxSize()) {
                if(keypoints.size > 3) {
                    drawCircle(Color.Blue, center= Offset(x=keypoints[0].second.toFloat(), y=keypoints[0].first.toFloat()))
                    drawCircle(Color.Blue, center= Offset(x=keypoints[1].second.toFloat(), y=keypoints[1].first.toFloat()))
                    drawCircle(Color.Blue, center= Offset(x=keypoints[2].second.toFloat(), y=keypoints[2].first.toFloat()))
                }
            }
        }
    } else {
        Column(modifier = modifier) {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                "Please enable the camera permissions."
            } else {
                "Camera permission required for this feature to be available. Please grant the permission"
            }
            Text(textToShow)
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Request permission")
            }
        }
    }

}