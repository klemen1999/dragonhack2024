package com.google.mediapipe.examples.poselandmarker.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.mediapipe.examples.poselandmarker.MainActivity
import com.google.mediapipe.examples.poselandmarker.compose.ui.features.home.HomeScreen
import com.google.mediapipe.examples.poselandmarker.compose.ui.features.login.RandomGenerateIdLogin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        RandomGenerateIdLogin(navController = navController)
                    }
                    composable("home") {
                        HomeScreen(
                            navController = navController
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun ButtonTest() {
    val context = LocalContext.current
    Button(onClick = {
        //open the .MainActivity
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }) {

    }
}
