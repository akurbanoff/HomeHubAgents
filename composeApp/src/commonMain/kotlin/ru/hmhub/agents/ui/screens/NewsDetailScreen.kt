package ru.hmhub.agents.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import homehubagents.composeapp.generated.resources.Res
import homehubagents.composeapp.generated.resources.ic_test_realstate_obj
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultTopAppBar

class NewsDetailScreen(
    val navigator: Navigator,
    val id: Int
) : Screen {
    @Composable
    override fun Content() {
        val element = remember {list[id]}
        val date = "14 Февраля 2024"
        val topTitle = "Новости"

        Scaffold(
            topBar = { DefaultTopAppBar(title = topTitle, navigator = navigator)},
            modifier = Modifier.padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                item {
                    TopNewsInfo(date = date)
                    NewsBody(newsElement = element)
                }
            }
        }
    }

    @Composable
    private fun TopNewsInfo(modifier: Modifier = Modifier, date: String){
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.LightGray,
                        shape = MaterialTheme.shapes.extraLarge
                    )
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Row {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .clickable {  }
                )
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .clickable {  }
                )
            }
        }
    }

    @Composable
    private fun NewsBody(modifier: Modifier = Modifier, newsElement: Pair<String, String>){
        Text(
            text = newsElement.first,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(Res.drawable.ic_test_realstate_obj),
            contentDescription = null,
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = newsElement.second,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}