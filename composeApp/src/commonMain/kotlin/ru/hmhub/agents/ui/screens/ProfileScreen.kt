package ru.hmhub.agents.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Battery2Bar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import homehubagents.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.data.in_memory.InMemoryHelper
import ru.hmhub.agents.ui.navigation.NavigationRoutes
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultBottomBar
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.seiko.imageloader.rememberImagePainter
import homehubagents.composeapp.generated.resources.ic_archive_card_back
import homehubagents.composeapp.generated.resources.ic_achievement_icon_back
import homehubagents.composeapp.generated.resources.ic_test_profile
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultTopAppBar
import ru.hmhub.agents.ui.view_models.RemoteViewModel

class ProfileScreen(
    val id: Int,
    val navigator: Navigator,
    val inMemoryHelper: InMemoryHelper,
    val remoteViewModel: RemoteViewModel
) : Screen {
    val currentUser = inMemoryHelper.getCurrentUser()

    @Composable
    override fun Content() {
        val title = "Профиль"

        val isUserAdmin = currentUser?.department?.name == "Руководство"
        Scaffold(
            topBar = { DefaultTopAppBar(title = title, navigator = navigator, isUserAdmin = isUserAdmin, isAccountShowable = !isUserAdmin, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel) },
            bottomBar = { DefaultBottomBar(navigator = navigator, currentPage = NavigationRoutes.ProfileScreen, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel) },
            modifier = Modifier.padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                item {
                    HeaderProfileInfo()
                    Spacer(modifier = Modifier.height(16.dp))
                    ArchiveBox(
                        text = "Достижения",
                        itemsAmount = list11.size,
                        onClick = { navigator.push(AchievementsDetailScreen(navigator = navigator, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel)) },
                        image = Res.drawable.ic_achievement_icon_back
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ArchiveBox(
                        text = "Сделки",
                        itemsAmount = list12.size,
                        onClick = {navigator.push(TradesScreen(navigator = navigator, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel))},
                        image = Res.drawable.ic_archive_card_back
                    )
                }
            }
        }
    }

    @Composable
    private fun HeaderProfileInfo(modifier: Modifier = Modifier){
        var progress by rememberSaveable { mutableStateOf(0.45f)}
        var progressBarColor = Color.Green
        if(progress < 0.5f){
            progressBarColor = Color.Red
        } else if(progress > 0.5f && progress < 0.75f){
            progressBarColor = Color.Yellow
        }
        Column{
            Box(
                modifier = modifier.fillMaxWidth()
            ) {
                Image(
                    painter = rememberImagePainter(currentUser?.photos?.first() ?: ""),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .height(height = 200.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center)
                )
                Column(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.AttachMoney,
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.CenterHorizontally),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "112 т.р.",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Text(
                text = "${currentUser?.last_name} ${currentUser?.first_name} ${currentUser?.middle_name}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Продвинутый",
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.size(4.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    color = progressBarColor,
                    modifier = Modifier.fillMaxSize(0.4f)
                )
                Spacer(modifier = Modifier.size(4.dp))
            }
            Column (
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Телефон:")
                    Text(text = currentUser?.phone ?: "Отсутствует")
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Почта:")
                    Text(text = currentUser?.email ?: "Отсутствует")
                }
            }
        }
    }
}

@Composable
fun ArchiveBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    itemsAmount: Int,
    image: DrawableResource
){
    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ){
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxHeight(),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                    //modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "$itemsAmount элементов"
                )
            }
        }
    }
}