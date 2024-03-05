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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import ru.hmhub.agents.ui.navigation.NavigationRoutes
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultBottomBar
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultTopAppBar

class AdminScreen(
    val navigator: Navigator
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    val llist = listOf<Pair<String, @Composable () -> Unit>>(
        "удалить сотрудника" to { BasicAlertDialog(onDismissRequest = { /*TODO*/ }) {} },
        "удалить сотрудника" to { BasicAlertDialog(onDismissRequest = { /*TODO*/ }) {} },
        "удалить сотрудника" to { BasicAlertDialog(onDismissRequest = { /*TODO*/ }) {} },
        "удалить сотрудника" to { BasicAlertDialog(onDismissRequest = { /*TODO*/ }) {} },
    )

    @Composable
    override fun Content() {
        val title = "Админ Панель"
        Scaffold(
            topBar = { DefaultTopAppBar(title = title, navigator = navigator) },
            bottomBar = { DefaultBottomBar(navigator = navigator, currentPage = NavigationRoutes.AdminScreen) },
            modifier = Modifier.padding(16.dp)
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                columns = GridCells.Fixed(2)
            ) {
                items(llist){
                    ActionMenuItem(title = it.first, action = it.second)
                }
            }
        }
    }

    @Composable
    private fun ActionMenuItem(title: String, action: @Composable () -> Unit) {
        Card(
            onClick = { action },
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray
            ),
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
                    onClick = { action },
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