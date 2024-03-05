package ru.hmhub.agents.ui.screens.general_ui_elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.West
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import homehubagents.composeapp.generated.resources.Res
import homehubagents.composeapp.generated.resources.ic_hubcoin
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.ui.navigation.NavigationRoutes
import ru.hmhub.agents.ui.screens.AdminScreen
import ru.hmhub.agents.ui.screens.MarketScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    title: String,
    isUserAdmin: Boolean = false,
    isMainPage: Boolean = false,
    isAccountShowable: Boolean = false,
    //user: User
    navigator: Navigator
){
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                //modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.displaySmall
            )
        },
        navigationIcon = {
            if(isMainPage){
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    modifier = Modifier.size(44.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.West,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            //navigator.safePopBackStack()
                            if(navigator.canPop) navigator.pop()
                        }
                        .size(44.dp)
                )
            }
        },
        actions = {
            if(isAccountShowable && !isUserAdmin){
                Row(
                    modifier = Modifier
                        .clickable {
                            navigator.push(MarketScreen(id = 0, navigator = navigator))
                        },
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "100",
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(Res.drawable.ic_hubcoin),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(30.dp)
                    )
                }
            } else if(isUserAdmin && !isAccountShowable){
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { navigator.push(AdminScreen(navigator = navigator)) }
                        .size(44.dp)
                )
            }
        }
    )
}