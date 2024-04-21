package com.google.mediapipe.examples.poselandmarker.compose.ui.features.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.mediapipe.examples.poselandmarker.core.PreferencesManager
import java.util.UUID

@Composable
fun RandomGenerateIdLogin(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val username = remember { mutableStateOf(TextFieldValue("")) }
    val preferencesManager = remember { PreferencesManager(context) }

    if (preferencesManager.getInt(PreferencesManager.INSTALL_ID) > 0) {
        navController.navigate("home")
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sweatify",
                style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight(900)),
                modifier = Modifier.padding(50.dp)
            )
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
                    //generate random number
                    val randomNum = (100..10000000).random()
                    preferencesManager.saveString(PreferencesManager.USERNAME, username.value.text)
                    preferencesManager.saveInt(PreferencesManager.INSTALL_ID, randomNum)
                    viewModel.login(randomNum, username.value.text)
                    navController.navigate("home")
                }
            }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007F8B)),
                modifier = Modifier.padding(10.dp)
                ) {
                Text(text = "Sign In", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(700))
                )
            }
        }
    }
}
