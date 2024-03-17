package ru.hmhub.agents.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.stack.popUntil
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.seiko.imageloader.rememberImagePainter
import homehubagents.composeapp.generated.resources.Res
import homehubagents.composeapp.generated.resources.ic_no_news_background
import homehubagents.composeapp.generated.resources.ic_test_profile
import homehubagents.composeapp.generated.resources.ic_test_realstate_obj
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.data.in_memory.InMemoryHelper
import ru.hmhub.agents.data.remote.responseSerializables.NewsSerializable
import ru.hmhub.agents.data.remote.serializables.EmployeeSerializable
import ru.hmhub.agents.ui.navigation.NavigationRoutes
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultBottomBar
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultTopAppBar
import ru.hmhub.agents.ui.screens.general_ui_elements.ScreenError
import ru.hmhub.agents.ui.view_models.RemoteViewModel

class NewsScreen (
    val remoteViewModel: RemoteViewModel,
    val inMemoryHelper: InMemoryHelper
): Screen {
    override val key: ScreenKey = "news"
    @Composable
    override fun Content() {
        val title = "Новости"
        val navigator: Navigator = LocalNavigator.currentOrThrow
        val newsList = remoteViewModel.getNews().collectAsLazyPagingItems()

        //сделать функцию получения конкретного юзера по id взятым из inMemoryHelper в bottomBar

        Scaffold(
            topBar = { DefaultTopAppBar(title = title, navigator = navigator, isMainPage = true, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel)},
            bottomBar = { DefaultBottomBar(navigator = navigator, currentPage = NavigationRoutes.NewsScreen, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel)},
            modifier = Modifier.padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                if(newsList.itemCount < 1){
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.ic_no_news_background),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxSize().align(Alignment.Center)
                            )
                        }
                    }
                } else {
                    items(newsList.itemCount) { newsIndex ->
                        NewsItem(news = newsList[newsIndex], navigator = navigator)
                    }
                }
            }
            when(val state = newsList.loadState.refresh){
                is LoadState.Error -> ScreenError(error = state.error, onRefresh = { newsList.refresh() })
                LoadState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Loading...",
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        CircularProgressIndicator()
                    }
                }
                is LoadState.NotLoading -> {}
            }

            when(val state = newsList.loadState.append){
                is LoadState.Error -> ScreenError(error = state.error, onRefresh = { newsList.refresh() })
                LoadState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Loading...",
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        CircularProgressIndicator()
                    }
                }
                is LoadState.NotLoading -> {}
            }
        }
    }

    @Composable
    private fun NewsItem(
        modifier: Modifier = Modifier,
        news: NewsSerializable?,
        navigator: Navigator
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)//, bottom = 16.dp)
            .clickable {
                navigator.push(NewsDetailScreen(navigator = navigator, news = news, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel))
            }
        ) {
            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = news?.createdAt ?: "",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Row {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(35.dp)
                                    .clickable {  }
                            )
                            Icon(
                                imageVector = Icons.Default.MoreHoriz,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(35.dp)
                                    .clickable {  }
                            )
                        }
                    }
                    Image(
                        painter = rememberImagePainter(news?.photos?.firstOrNull() ?: ""),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .height(200.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillBounds
                    )
                    Text(
                        text = news?.title ?: "",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}