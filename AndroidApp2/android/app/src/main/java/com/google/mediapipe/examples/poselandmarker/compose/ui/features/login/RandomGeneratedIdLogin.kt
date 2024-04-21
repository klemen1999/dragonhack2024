package com.google.mediapipe.examples.poselandmarker.compose.ui.features.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.google.mediapipe.examples.poselandmarker.core.PreferencesManager
import java.util.UUID

@Composable
fun RandomGenerateIdLogin(
    navController: NavController,
) {
    val context = LocalContext.current
    val username = remember { mutableStateOf(TextFieldValue("")) }
    val preferencesManager = remember { PreferencesManager(context) }

    if (preferencesManager.getString(PreferencesManager.INSTALL_ID) != null) {
        navController.navigate("home")
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = username.value,
                onValueChange = {
                    username.value = it
                },
                label = { Text("Username") },
                placeholder = { Text("Enter your username") }
            )
            Button(onClick = {
                if (username.value.text.isNotEmpty()) {
                    val randomString = UUID.randomUUID().toString()
                    preferencesManager.saveString(PreferencesManager.USERNAME, username.value.text)
                    preferencesManager.saveString(PreferencesManager.INSTALL_ID, randomString)
                    navController.navigate("home")
                }
            }) {
                Text(text = "Sign In")
            }
        }
    }
}
