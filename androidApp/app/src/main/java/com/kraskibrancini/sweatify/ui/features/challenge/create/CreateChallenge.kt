package com.kraskibrancini.sweatify.ui.features.challenge.create

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kraskibrancini.sweatify.ui.features.challenge.ChallengeViewModel

@Composable
fun CreateChallenge(
    viewModel: ChallengeViewModel = hiltViewModel()
) {
    
}

@Composable
fun CreateChallengeContent(
    createChallenge: () -> Unit
) {
    Column {

    }
}

@Preview
@Composable
fun CreateChallengePreview() = MaterialTheme {
    CreateChallengeContent(
        createChallenge = {}
    )
}