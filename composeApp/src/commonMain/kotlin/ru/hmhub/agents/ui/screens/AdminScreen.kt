package ru.hmhub.agents.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import ru.hmhub.agents.data.in_memory.InMemoryHelper
import ru.hmhub.agents.ui.navigation.NavigationRoutes
import ru.hmhub.agents.ui.screens.admin_feature_screens.AddNewsScreen
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultBottomBar
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultTopAppBar
import ru.hmhub.agents.ui.view_models.RemoteViewModel

class AdminScreen(
    val navigator: Navigator,
    val inMemoryHelper: InMemoryHelper,
    val remoteViewModel: RemoteViewModel
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    val actionTitleList = listOf(
        "Добавить новость" to 1,
        "Удалить новость" to 2,
    )

    @Composable
    override fun Content() {
        val title = "Админ Панель"
        Scaffold(
            topBar = { DefaultTopAppBar(title = title, navigator = navigator, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel) },
            bottomBar = { DefaultBottomBar(navigator = navigator, currentPage = NavigationRoutes.AdminScreen, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel) },
            modifier = Modifier.padding(16.dp)
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                columns = GridCells.Fixed(2)
            ) {
                items(actionTitleList){
                    ActionMenuItem(title = it.first, actionId = it.second)
                }
            }
        }
    }

    @Composable
    private fun ActionMenuItem(title: String, actionId: Int) {
        var showDialog = rememberSaveable { mutableStateOf(false) }
        var countTimes = rememberSaveable { mutableStateOf(0) }

        if(showDialog.value) {
            when (actionId) {
                1 -> {
                    if(countTimes.value < 1) {
                        countTimes.value += 1
                        navigator.push(
                            AddNewsScreen(
                                navigator = navigator,
                                inMemoryHelper = inMemoryHelper,
                                remoteViewModel = remoteViewModel
                            )
                        )
                    }
                }

                2 -> {}
            }
        }

        Card(
            onClick = { showDialog.value = true },
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),
            modifier = Modifier.padding(6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Text(
                    text = title,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showDialog.value = true },
                    modifier = Modifier
                        .align(Alignment.End)
                        .size(40.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }
    }
}