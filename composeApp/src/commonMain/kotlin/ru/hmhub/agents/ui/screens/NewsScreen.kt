package ru.hmhub.agents.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

val list = listOf(
    "What is Lorem Ipsum?" to "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
    "What is Lorem Ipsum?" to "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
    "What is Lorem Ipsum?" to "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
    "What is Lorem Ipsum?" to "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
)

class NewsScreen(
    val navigator: Navigator
): Screen {
    @Composable
    override fun Content() {
        val title = "Новости"
        Scaffold(
            topBar = { DefaultTopAppBar(title = title, navigator = navigator, isMainPage = true)},
            bottomBar = { DefaultBottomBar(navigator = navigator, currentPage = NavigationRoutes.NewsScreen)},
            modifier = Modifier.padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                items(list){
                    NewsItem(title = it.first, text = it.second, navigator = navigator)
                }
            }
        }
    }

    @Composable
    fun NewsItem(title: String, text: String, navigator: Navigator) {
        val date = "14 Февраля 2024"
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable {
                //navigator.navigate(NavigationRoutes.NewsDetailScreen.withArgs(0))
                navigator.push(NewsDetailScreen(navigator = navigator, id = 0))
            }
        ) {
            Card(
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = if (text.length > 50) text.take(150).plus("...") else text,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    Text(
                        text = date,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Button(
                onClick = { navigator.push(NewsDetailScreen(navigator = navigator, id = 0)) },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(60.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowRightAlt,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    }
}