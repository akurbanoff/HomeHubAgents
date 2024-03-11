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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import homehubagents.composeapp.generated.resources.ic_achieve_car
import homehubagents.composeapp.generated.resources.ic_achievement_icon_back
import homehubagents.composeapp.generated.resources.ic_archive_card_back
import homehubagents.composeapp.generated.resources.ic_test_profile
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.ui.navigation.NavigationRoutes
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultBottomBar
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultTopAppBar

class ProfileScreen(
    val id: Int,
    val navigator: Navigator
) : Screen {
    @Composable
    override fun Content() {
        val title = "Профиль"
        Scaffold(
            topBar = { DefaultTopAppBar(title = title, navigator = navigator, isAccountShowable = true) },
            bottomBar = { DefaultBottomBar(navigator = navigator, currentPage = NavigationRoutes.ProfileScreen) },
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
                        onClick = { navigator.push(AchievementsDetailScreen(navigator = navigator, userId = id)) },
                        image = Res.drawable.ic_achievement_icon_back
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ArchiveBox(
                        text = "Сделки",
                        itemsAmount = list12.size,
                        onClick = {navigator.push(TradesScreen(navigator = navigator, id = 0))},
                        image = Res.drawable.ic_archive_card_back
                    )
                }
//                items(list11){
//                    Achievement(title = it.first, photo = it.second)
//                }
//                items(list12){
//                    RealStateObj(title = it.first, photos = it.second, navigator = navigator)
//                }
            }
        }
    }

    @Composable
    private fun HeaderProfileInfo(modifier: Modifier = Modifier){
        var progress by remember { mutableStateOf(0.45f)}
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
                    painter = painterResource(Res.drawable.ic_test_profile),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .align(Alignment.Center)
                        .size(height = 250.dp, width = 190.dp)
                )
                Column(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.AttachMoney,
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "112 т.р.",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Text(
                text = "Курбанов Артем Евгеньевич",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
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
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.size(4.dp))
                LinearProgressIndicator(
                    progress = { 0.2f },
                    color = progressBarColor,
                    modifier = Modifier.fillMaxSize(0.4f)
                )
                Spacer(modifier = Modifier.size(4.dp))
            }
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Телефон:")
                    Text(text = "+7 900 241 09 81")
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Дата рождения:")
                    Text(text = "10.09.2003 г.")
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