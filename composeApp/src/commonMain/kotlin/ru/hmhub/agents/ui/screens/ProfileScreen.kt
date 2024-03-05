package ru.hmhub.agents.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Battery2Bar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import homehubagents.composeapp.generated.resources.Res
import homehubagents.composeapp.generated.resources.ic_test_profile
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
                    Column{
                        Box(
                            modifier = Modifier.fillMaxWidth()
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
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.Center
                        ){
                            Text(
                                text = "Продвинутый",
                                modifier = Modifier,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Battery2Bar,
                                    contentDescription = null
                                )
                                Text(
                                    text = "20 %",
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
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
                        Text(
                            text = "Последние достижения",
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 6.dp),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                items(list11){
                    Achievement(title = it.first, photo = it.second)
                }
                item {
                    Text(
                        text = "Архив сделок",
                        modifier = Modifier.fillMaxWidth().padding(top = 6.dp).clickable { navigator.push(TradesScreen(id = 0, navigator = navigator)) },
                        textAlign = TextAlign.Center,
                        //fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                items(list12){
                    RealStateObj(title = it.first, photos = it.second, navigator = navigator)
                }
            }
        }
    }
}