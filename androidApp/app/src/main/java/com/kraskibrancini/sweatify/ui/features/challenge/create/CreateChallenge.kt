package com.kraskibrancini.sweatify.ui.features.challenge.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kraskibrancini.sweatify.ui.features.challenge.ChallengeViewModel

@Composable
fun CreateChallenge(
    viewModel: ChallengeViewModel = hiltViewModel()
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateChallengeContent(
    createChallenge: (Challenge) -> Unit
) {
    var challengeName by remember { mutableStateOf(TextFieldValue("")) }
    var challengeDescription by remember { mutableStateOf(TextFieldValue("")) }
    var challengeType by remember { mutableStateOf(TextFieldValue("")) }
    var challengeEndTime by remember { mutableStateOf(TextFieldValue("")) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = challengeName,
            onValueChange = { challengeName = it },
            label = { Text("Challenge Name") })
        TextField(value = challengeDescription,
            onValueChange = { challengeDescription = it },
            label = { Text("Challenge Description") })
        TextField(value = challengeType,
            onValueChange = { challengeType = it },
            label = { Text("Challenge Type") })
        TextField(value = challengeEndTime,
            onValueChange = { challengeEndTime = it },
            label = { Text("Challenge End Time") })
        Button(onClick = { createChallenge(
            Challenge(
                name = challengeName.text,
                description = challengeDescription.text,
                type = challengeType.text,
                endTime = challengeEndTime.text
            )
        ) }) {
            Text("Create Challenge")
        }
    }
}

@Preview
@Composable
fun CreateChallengePreview() = MaterialTheme {
    CreateChallengeContent(
        createChallenge = {}
    )
}