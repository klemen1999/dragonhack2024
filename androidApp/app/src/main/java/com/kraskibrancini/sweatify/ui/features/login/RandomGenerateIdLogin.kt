package com.kraskibrancini.sweatify.ui.features.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.kraskibrancini.sweatify.core.PreferencesManager
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomGenerateIdLogin(
    navController: NavController,
) {
    val context = LocalContext.current
    var username by remember { mutableStateOf(TextFieldValue("")) }
    val preferencesManager = remember { PreferencesManager(context) }

    if(preferencesManager.getString(PreferencesManager.INSTALL_ID) != null) {
        navController.navigate("home")
    } else {


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = { Text("Username") },
                placeholder = { Text("Enter your username") }
            )
            Button(onClick = {
                if(username.text.isNotEmpty()) {
                    val randomString = UUID.randomUUID().toString()
                    preferencesManager.saveString(PreferencesManager.USERNAME, username.text)
                    preferencesManager.saveString(PreferencesManager.INSTALL_ID, randomString)
                    navController.navigate("home")
                }
            }) {
                Text(text = "Sign In")
            }
        }
    }
}