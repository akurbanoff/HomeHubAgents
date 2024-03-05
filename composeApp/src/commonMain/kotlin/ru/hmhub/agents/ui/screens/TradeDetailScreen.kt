package ru.hmhub.agents.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
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
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultTopAppBar

class TradeDetailScreen(
    val id: Int,
    val navigator: Navigator
) : Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val title = "Архив Сделок"
        val tradeObj = list12[id]
        Scaffold(
            topBar = { DefaultTopAppBar(title = title, navigator = navigator)},
            modifier = Modifier.padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ){
                item {
                    Text(
                        text = tradeObj.first,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                }
                item {
                    HorizontalPager(
                        state = rememberPagerState {tradeObj.second.size}
                    ) { photoId ->
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = painterResource(tradeObj.second[photoId]),
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.medium)
                                    .align(Alignment.Center)
                                    .fillMaxWidth()
                                    .height(230.dp),
                                contentScale = ContentScale.FillBounds
                            )
                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .padding(vertical = 8.dp)
                                    .align(Alignment.BottomCenter),
                                //.background(Color.White),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                repeat(tradeObj.second.size){index ->
                                    val color = if(index == photoId) Color.Black else Color.White
                                    val isCurrent = index == photoId
                                    Icon(
                                        imageVector = if(isCurrent) Icons.Default.Circle else Icons.Default.Circle,
                                        contentDescription = null,
                                        tint = color,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .padding(horizontal = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .background(color = Color.LightGray, shape = MaterialTheme.shapes.medium)
                            .padding(12.dp)
                    ) {
                        RealStateDesc(name = "Кол-во комнат:", value = "1")
                        RealStateDesc(name = "Площадь общая, м2:", value = "43,2")
                        RealStateDesc(name = "Площадь жилая, м2:", value = "20,8")
                        RealStateDesc(name = "Площадь кухни, м2:", value = "12,4")
                        RealStateDesc(name = "Количество комнат:", value = "1")
                        RealStateDesc(name = "Отопление:", value = "Газ")
                        RealStateDesc(name = "Балкон:", value = "Застекленная лоджия")
                        RealStateDesc(name = "Ремонт:", value = "Евро")
                    }
                    Text(
                        text = "Описание",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 8.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(text = "Срочная продажа, лучшая цена в комплексе. Новый жилой комплекс  \"Версаль\" Продаю квартиру площадью 43,2 м2 + большая лоджия. Вид на парк КРАСНОДАР, хорошее остекление открывает превосходный вид на окрестности в любое время суток. Квартира в предчистовой отделке, идеальная стяжка, эл проводка проложена, есть место под просторную гардеробную, балкон застеклён. Закрытая территория, охрана, видеонаблюдение, парковка во дворе и перед домом. Территория дома выходит на набережную небольшого озера. Тихое и спокойное место для проживания или сдачи в аренду. Рядом остановки, детский сад, школа, рынок, магазины.")
                }
            }
        }
    }

    @Composable
    private fun RealStateDesc(name: String, value: String) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = name)
            Text(text = value)
        }
    }
}