package com.google.mediapipe.examples.poselandmarker.compose.ui.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.mediapipe.examples.poselandmarker.api.dto.ChallengeDto
import com.google.mediapipe.examples.poselandmarker.compose.ui.features.home.models.HomeData
import com.google.mediapipe.examples.poselandmarker.compose.ui.features.home.models.HomeScreenState
import com.google.mediapipe.examples.poselandmarker.core.formatDateToString
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getChallenges()
    }

    when (state.value) {
        is HomeScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is HomeScreenState.Loaded -> {
            HomeScreenContent(
                modifier = Modifier,
                challenges = (state.value as HomeScreenState.Loaded).homeData?.challenges
                    ?: emptyList(),
                onJoinChallengeClick = viewModel::joinChallenge,
                goToChallenge = {
                    navController.navigate("challenge/$it")
                }
            )
        }
    }


}


@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    challenges: List<ChallengeDto>,
    onJoinChallengeClick: (String) -> Unit,
    goToChallenge: (String) -> Unit
) {
    val challengeTab = remember { mutableStateOf(true) }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Home")
            Icon(
                Icons.Default.AccountCircle,
                modifier = Modifier.size(24.dp),
                contentDescription = "Account"
            )
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Challenges",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { challengeTab.value = true },
                color = if (challengeTab.value) Color.Blue else Color.Black
            )
            Divider(
                thickness = 1.dp, modifier = Modifier
                    .height(24.dp)
                    .width(1.dp)
            )
            Text(
                text = "My Activity",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { challengeTab.value = false },
                color = if (!challengeTab.value) Color.Blue else Color.Black
            )
        }

        ChallengeListContent(
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize(),
            challenges = challenges,
            onJoinChallengeClick = onJoinChallengeClick
        )


    }

}

@Composable
fun ChallengeListContent(
    modifier: Modifier = Modifier,
    challenges: List<ChallengeDto>,
    onJoinChallengeClick: (String) -> Unit
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        challenges.forEach { challenge ->
            ChallengeCard(
                modifier = Modifier.padding(vertical = 8.dp),
                title = challenge.exerciseType ?: "Unknown",
                description = challenge.description ?: "No description",
                participants = challenge.participants?.map { it ?: "" } ?: emptyList(),
                dateStart = challenge.startTime ?: "",
                dateEnd = challenge.endTime ?: "",
                onJoinChallengeClick = {
                    println("Joining challenge")
                    onJoinChallengeClick(challenge._id)
                }
            )
        }
    }
}

@Composable
fun ActivityListContent(
    modifier: Modifier = Modifier,
    challenges: List<ChallengeDto>,
) {
    LazyColumn(modifier = modifier) {
        items(challenges) { challenge ->
            ChallengeCard(
                modifier = Modifier.padding(vertical = 8.dp),
                title = challenge.exerciseType ?: "Unknown",
                description = challenge.description ?: "No description",
                participants = challenge.participants?.map { it ?: "" } ?: emptyList(),
                dateStart = challenge.startTime ?: "",
                dateEnd = challenge.endTime ?: "",
                onJoinChallengeClick = {
                }
            )
        }
    }
}

@Composable
fun ChallengeCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    participants: List<String>,
    dateStart: String,
    dateEnd: String,
    onJoinChallengeClick: () -> Unit
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = title)
            Text(text = description)
            Text(text = "Participants: ${participants.size}")
            Text(text = "Start: ${dateStart}")
            Text(text = "End: ${dateEnd}")
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp), thickness = 1.dp
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = onJoinChallengeClick,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(text = "Join")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenContentPreview() = MaterialTheme {
    val stateFlow =
        HomeScreenState.Loaded(
            HomeData(
                listOf(
                    ChallengeDto(
                        _id = "1",
                        participants = emptyList(),
                        exerciseType = "Pushups",
                        exercises = emptyList(),
                        startTime = "Just now",
                        endTime = "Just now",
                        recurrence = 1,
                        description = "Do as many pushups as you can"
                    ),
                )
            )
        )


    HomeScreenContent(
        challenges = stateFlow.homeData.challenges,
        onJoinChallengeClick = {},
        goToChallenge = {}
    )
}