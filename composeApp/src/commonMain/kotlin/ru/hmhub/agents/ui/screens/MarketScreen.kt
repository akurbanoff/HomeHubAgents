package ru.hmhub.agents.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import homehubagents.composeapp.generated.resources.ic_hubcoin
import homehubagents.composeapp.generated.resources.ic_market_car
import homehubagents.composeapp.generated.resources.ic_market_holiday
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultBottomBar
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultTopAppBar
import ru.hmhub.agents.ui.navigation.NavigationRoutes

class MarketScreen(
    val id: Int,
    val navigator: Navigator
) : Screen {
    @Composable
    override fun Content() {
        val title = "Магазин"
        Scaffold(
            topBar = { DefaultTopAppBar(title = title, navigator = navigator, isAccountShowable = true) },
            bottomBar = { DefaultBottomBar(navigator = navigator, currentPage = NavigationRoutes.MarketScreen) },
            modifier = Modifier.padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                item {
                    SingleMarketElement(title = "автомобиль", image = Res.drawable.ic_market_car, price = 1_000_000, photoAlignment = Alignment.CenterStart)
                    DoubleMarketElement(
                        firstTitle = "10 баннеров",
                        firstPrice = 500,
                        secondTitle = "следующий общий клиент",
                        secondPrice = 700
                    )
                    SingleMarketElement(title = "отпуск на 30 дней", image = Res.drawable.ic_market_holiday, price = 5_000, photoAlignment = Alignment.CenterEnd)
                    DoubleMarketElement(
                        firstTitle = "10 баннеров",
                        firstPrice = 500,
                        secondTitle = "следующий общий клиент",
                        secondPrice = 700
                    )
                }
            }
        }
    }
    @Composable
    private fun SingleMarketElement(title: String, image: DrawableResource, price: Int, photoAlignment: Alignment) {
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(150.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(color = Color.LightGray)
        ){
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                alignment = photoAlignment,
                alpha = 0.5f,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                //.padding(start = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .background(color = Color.White, shape = MaterialTheme.shapes.small)
                        .padding(4.dp)
                ) {
                    Text(
                        text = price.toString(),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Image(
                        painter = painterResource(Res.drawable.ic_hubcoin),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(30.dp)
                    )
                }
            }
        }
    }

    @Composable
    private fun DoubleMarketElement(firstTitle: String, firstPrice: Int, secondTitle: String, secondPrice: Int) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            SmallElement(title = firstTitle, price = firstPrice)
            SmallElement(title = secondTitle, price = secondPrice)
        }
    }

    @Composable
    private fun SmallElement(title: String, price: Int) {
        Column(
            modifier = Modifier
                .size(height = 150.dp, width = 150.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .background(color = Color.White, shape = MaterialTheme.shapes.medium)
                        .padding(4.dp)
                ) {
                    Text(
                        text = price.toString(),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Image(
                        painter = painterResource(Res.drawable.ic_hubcoin),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(30.dp)
                    )
                }
            }
        }
    }
}