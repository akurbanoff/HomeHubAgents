package ru.hmhub.agents.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import homehubagents.composeapp.generated.resources.Res
import homehubagents.composeapp.generated.resources.ic_test_realstate_obj
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.ui.navigation.NavigationRoutes
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultBottomBar
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultTopAppBar

val list12 = listOf(
    "Студия 27 кв.м., р-н Энка" to listOf(Res.drawable.ic_test_realstate_obj, Res.drawable.ic_test_realstate_obj, Res.drawable.ic_test_realstate_obj, Res.drawable.ic_test_realstate_obj),
    "Студия 27 кв.м., р-н Энка" to listOf(Res.drawable.ic_test_realstate_obj, Res.drawable.ic_test_realstate_obj, Res.drawable.ic_test_realstate_obj, Res.drawable.ic_test_realstate_obj),
    "Студия 27 кв.м., р-н Энка" to listOf(Res.drawable.ic_test_realstate_obj, Res.drawable.ic_test_realstate_obj, Res.drawable.ic_test_realstate_obj, Res.drawable.ic_test_realstate_obj),
    "Студия 27 кв.м., р-н Энка" to listOf(Res.drawable.ic_test_realstate_obj, Res.drawable.ic_test_realstate_obj, Res.drawable.ic_test_realstate_obj, Res.drawable.ic_test_realstate_obj),
)

class TradesScreen(
    val id: Int,
    val navigator: Navigator
) : Screen {
    @Composable
    override fun Content() {
        val title = "Архив Сделок"
        Scaffold(
            topBar = { DefaultTopAppBar(title = title, navigator = navigator) },
            bottomBar = { DefaultBottomBar(navigator = navigator, currentPage = NavigationRoutes.TradesScreen) },
            modifier = Modifier.padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                items(list12){
                    RealStateObj(title = it.first, photos = it.second, navigator = navigator)
                }
            }
        }
    }
}

@Composable
fun RealStateObj(title: String, photos: List<DrawableResource>, navigator: Navigator) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable { navigator.push(TradeDetailScreen(id = 0, navigator = navigator)) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(photos) {
                    Image(
                        painter = painterResource(it),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(MaterialTheme.shapes.small)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "14 Февраля 2024 г.",
                    modifier = Modifier.align(Alignment.Bottom)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.AttachMoney, contentDescription = null)
                    Text(
                        text = "3,2 млн руб.",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}