package ru.hmhub.agents.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import homehubagents.composeapp.generated.resources.Res
import homehubagents.composeapp.generated.resources.ic_hubcoin
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.ui.navigation.NavigationRoutes
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultBottomBar
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultTopAppBar

class AchievementsDetailScreen(
    val navigator: Navigator,
    val userId: Int? = null
) : Screen {
    @Composable
    override fun Content() {
        Scaffold(
            topBar = { DefaultTopAppBar(title = "Достижения", navigator = navigator) },
            bottomBar = { DefaultBottomBar(navigator = navigator, currentPage = NavigationRoutes.AchievementsDetailScreen) },
            modifier = Modifier.padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(list11){
                    Achievement(title = it.first, photo = it.second)
                }
            }
        }
    }
    @Composable
    fun Achievement(title: String, photo: DrawableResource) {
        val HCAmount = 1
        val expAmount = 5
        var progress by remember { mutableStateOf(0.45f) }
        var progressBarColor = Color.Green
        if(progress < 0.5f){
            progressBarColor = Color.Red
        } else if(progress > 0.5f && progress < 0.75f){
            progressBarColor = Color.Yellow
        }
        Card(
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Box(modifier = Modifier) {
//                    Image(
//                        painter = painterResource(photo),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .align(Alignment.CenterStart)
//                            .clip(CircleShape)
//                            .size(70.dp)
//                    )
                        Icon(
                            imageVector = Icons.Outlined.EmojiEvents,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(70.dp)
                        )
                    }
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                HorizontalDivider(
                    color = Color.Black,
                    thickness = 2.dp
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        progress = { 0.5f },
                        color = progressBarColor,
                        modifier = Modifier.fillMaxSize(0.4f)
                    )
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$HCAmount",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Image(
                            painter = painterResource(Res.drawable.ic_hubcoin),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "$expAmount EXP",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}