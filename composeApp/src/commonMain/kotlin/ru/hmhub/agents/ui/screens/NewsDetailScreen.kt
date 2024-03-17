package ru.hmhub.agents.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.seiko.imageloader.rememberImagePainter
import homehubagents.composeapp.generated.resources.Res
import homehubagents.composeapp.generated.resources.ic_test_realstate_obj
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.data.in_memory.InMemoryHelper
import ru.hmhub.agents.data.remote.responseSerializables.NewsSerializable
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultTopAppBar
import ru.hmhub.agents.ui.view_models.RemoteViewModel
import kotlin.math.absoluteValue

class NewsDetailScreen(
    val navigator: Navigator,
    val news: NewsSerializable?,
    val inMemoryHelper: InMemoryHelper,
    val remoteViewModel: RemoteViewModel
) : Screen {
    @Composable
    override fun Content() {
        val topTitle = "Новости"

        Scaffold(
            topBar = { DefaultTopAppBar(title = topTitle, navigator = navigator, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel)},
            modifier = Modifier.padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                item {
                    TopNewsInfo(date = news?.createdAt ?: "")
                    NewsBody()
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
            Text(
                text = date,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(16.dp)
            )
            Row {
                Icon(
                    imageVector = Icons.Default.Share,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .clickable {  }
                )
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(44.dp)
                        .clickable {  }
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun NewsBody(
        modifier: Modifier = Modifier
    ){
        val pagerState = rememberPagerState(pageCount = {news?.photos?.size ?: 0})
        var photoId by rememberSaveable{ mutableIntStateOf(0) }

        Text(
            text = news?.title ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) { currentPhotoId ->
            photoId = currentPhotoId
            val pageOffset =
                (pagerState.currentPage - photoId) + pagerState.currentPageOffsetFraction

            val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    .alpha(
                        scaleFactor.coerceIn(0f, 1f)
                    )
                    //.padding(10.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = rememberImagePainter(news?.photos?.get(photoId) ?: ""),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .height(230.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
//        Image(
//            painter = painterResource(Res.drawable.ic_test_realstate_obj),
//            contentDescription = null,
//            modifier = Modifier
//                .clip(MaterialTheme.shapes.medium)
//                .height(200.dp)
//                .fillMaxWidth(),
//            contentScale = ContentScale.FillBounds
//        )
        Text(
            text = news?.description ?: "",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}