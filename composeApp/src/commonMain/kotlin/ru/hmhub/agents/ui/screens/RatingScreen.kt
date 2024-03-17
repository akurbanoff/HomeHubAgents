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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import homehubagents.composeapp.generated.resources.Res
import homehubagents.composeapp.generated.resources.ic_hubcoin
import homehubagents.composeapp.generated.resources.ic_photo
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.data.in_memory.InMemoryHelper
import ru.hmhub.agents.ui.navigation.NavigationRoutes
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultBottomBar
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultTopAppBar
import ru.hmhub.agents.ui.view_models.RemoteViewModel

private val list1 = listOf<Pair<String, DrawableResource>>(
    "Ребров A.B." to Res.drawable.ic_photo,
    "Ребров A.B." to Res.drawable.ic_photo,
    "Ребров A.B." to Res.drawable.ic_photo,
    "Ребров A.B." to Res.drawable.ic_photo,
    "Ребров A.B." to Res.drawable.ic_photo,
)

class RatingScreen(
    val navigator: Navigator,
    val inMemoryHelper: InMemoryHelper,
    val remoteViewModel: RemoteViewModel
) : Screen {
    @Composable
    override fun Content() {
        val title = "Рейтинг"

        var openMenu = rememberSaveable { mutableStateOf(false) }

        Scaffold(
            topBar = { DefaultTopAppBar(title = title, navigator = navigator, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel) },
            bottomBar = { DefaultBottomBar(navigator = navigator, currentPage = NavigationRoutes.RatingScreen, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel) },
            modifier = Modifier.padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                item {
                    HeaderElement()
                }
                item {
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            OutlinedButton(
                                onClick = { openMenu.value = !openMenu.value },
                                shape = MaterialTheme.shapes.medium,
                                contentPadding = PaddingValues(4.dp),
                                modifier = Modifier.align(Alignment.CenterEnd),
                                colors = ButtonDefaults.outlinedButtonColors()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Menu(openMenu = openMenu)
                        }
                    }
                }
                itemsIndexed(list1){ index, item ->
                    RatingPerson(name = item.first, photo = item.second, navigator = navigator, index = index + 1)
                }
            }
        }
    }

    @Composable
    private fun HeaderElement(modifier: Modifier = Modifier){
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "ТОП 10",
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Center),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }

    @Composable
    private fun Menu(modifier: Modifier = Modifier, openMenu: MutableState<Boolean>){
        DropdownMenu(
            expanded = openMenu.value,
            onDismissRequest = { openMenu.value = false },
            modifier = Modifier.clip(MaterialTheme.shapes.medium)
        ) {
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AttachMoney,
                        contentDescription = null
                    )
                },
                text = {
                    Text(text = "по доходу")
                },
                onClick = { /*TODO*/ }
            )
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.MilitaryTech,
                        contentDescription = null
                    )
                },
                text = {
                    Text(text = "по рангу")
                },
                onClick = { /*TODO*/ }
            )
            DropdownMenuItem(
                leadingIcon = {
                    Image(
                        painter = painterResource(Res.drawable.ic_hubcoin),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(20.dp)
                    )
                },
                text = {
                    Text(text = "по количеству HC")
                },
                onClick = { /*TODO*/ }
            )
        }
    }

    @Composable
    private fun RatingPerson(name: String, photo: DrawableResource, navigator: Navigator, index: Int) {
        val rank = "продвинутый"
        val achieve = "10,5 млн р."

        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .fillMaxWidth()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface)
                .clickable {
                    navigator.push(ProfileScreen(navigator = navigator, id = 0, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel))
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Image(
                    painter = painterResource(photo),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(height = 70.dp, width = 70.dp)
                        .align(Alignment.CenterVertically),
                    contentScale = ContentScale.FillBounds,
                )
                Column(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = name,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = rank,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Column(
                modifier = Modifier.align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = index.toString(),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = achieve,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}