package com.kraskibrancini.sweatify.ui.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kraskibrancini.sweatify.core.formatDateToString
import com.kraskibrancini.sweatify.models.Challenge
import com.kraskibrancini.sweatify.ui.features.home.models.HomeData
import com.kraskibrancini.sweatify.ui.features.home.models.HomeScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getChallenges()
    }
    val state = viewModel.state.collectAsState()

    HomeScreenContent(
        modifier = Modifier,
        state = state,
        onJoinChallengeClick = viewModel::joinChallenge
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: State<HomeScreenState>,
    onJoinChallengeClick: (Int) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Home")
                Icon(Icons.Default.AccountCircle,
                    modifier = Modifier.size(24.dp),
                    contentDescription = "Account")
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Add, contentDescription = "Add challenge")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padVal ->
        when (state.value) {
            is HomeScreenState.Loading -> {
                Box(
                    modifier = modifier
                        .padding(padVal)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is HomeScreenState.Loaded -> {
                ChallengeListContent(
                    modifier = modifier
                        .padding(padVal)
                        .fillMaxSize(),
                    challenges = (state.value as HomeScreenState.Loaded).homeData.challenges,
                    onJoinChallengeClick = onJoinChallengeClick
                )
            }
        }
    }
}

@Composable
fun ChallengeListContent(
    modifier: Modifier = Modifier,
    challenges: List<Challenge>,
    onJoinChallengeClick: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(challenges) { challenge ->
            ChallengeCard(
                modifier = Modifier.padding(vertical = 8.dp),
                title = challenge.title,
                description = challenge.description,
                participants = challenge.participants,
                dateStart = challenge.start,
                dateEnd = challenge.end,
                onJoinChallengeClick = {
                    onJoinChallengeClick(challenge.id)
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
    dateStart: LocalDateTime,
    dateEnd: LocalDateTime,
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
            Text(text = "Start: ${formatDateToString(dateStart)}")
            Text(text = "End: ${formatDateToString(dateEnd)}")
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
    val stateFlow = MutableStateFlow<HomeScreenState>(
        HomeScreenState.Loaded(
            HomeData(
                listOf(
                    Challenge(
                        id = 1,
                        title = "Rokice Challenge",
                        description = "Naredi 10 sklec vsak dan 7 dni.",
                        start = LocalDateTime.now(),
                        end = LocalDateTime.now().plusDays(7),
                        participants = listOf("Aljosa Koren", "Klemen Skrlj")
                    ),
                    Challenge(
                        id = 2,
                        title = "Kdo je jači?",
                        description = "Kdo bo naredil več počepov v 1 minuti?",
                        start = LocalDateTime.now(),
                        end = LocalDateTime.now().plusDays(7),
                        participants = emptyList(),
                        isJoined = true
                    )
                )
            )
        )
    )
    val state = stateFlow.collectAsState()

    HomeScreenContent(
        state = state,
        onJoinChallengeClick = {}
    )
}