package ru.hmhub.agents.ui.screens.general_ui_elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import ru.hmhub.agents.ui.navigation.NavigationRoutes
import ru.hmhub.agents.ui.screens.AchievementsScreen
import ru.hmhub.agents.ui.screens.NewsScreen
import ru.hmhub.agents.ui.screens.ProfileScreen
import ru.hmhub.agents.ui.screens.RatingScreen

@Composable
fun DefaultBottomBar(
    navigator: Navigator,
    currentPage: NavigationRoutes
){
    val iconsSize = 44.dp
    var isAppsIconClicked by remember { mutableStateOf(false) }
    BottomAppBar(
        containerColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Newspaper,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (currentPage != NavigationRoutes.NewsScreen) {
                            navigator.push(NewsScreen(navigator = navigator))
                        }
                    }
                    .size(iconsSize),
                tint = if(currentPage == NavigationRoutes.NewsScreen) MaterialTheme.colorScheme.primary else Color.Black
            )
            Icon(
                imageVector = Icons.Outlined.EmojiEvents,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (currentPage != NavigationRoutes.AchievementsScreen) {
                            //navigator.navigate(NavigationRoutes.AchievementsScreen.route)
                            navigator.push(AchievementsScreen(navigator = navigator))
                        }
                    }
                    .size(iconsSize),
                tint = if(currentPage == NavigationRoutes.AchievementsScreen) MaterialTheme.colorScheme.primary else Color.Black
            )
            Icon(
                imageVector = Icons.Outlined.Apps,
                contentDescription = null,
                modifier = Modifier
                    .clickable { isAppsIconClicked = !isAppsIconClicked }
                    .size(iconsSize),
                tint = if(isAppsIconClicked) MaterialTheme.colorScheme.primary else Color.Black
            )
            Icon(
                imageVector = Icons.Outlined.Leaderboard,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (currentPage != NavigationRoutes.RatingScreen) {
                            //navigator.navigate(NavigationRoutes.RatingScreen.route)
                            navigator.push(RatingScreen(navigator = navigator))
                        }
                    }
                    .size(iconsSize),
                tint = if(currentPage == NavigationRoutes.RatingScreen) MaterialTheme.colorScheme.primary else Color.Black
            )
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (currentPage != NavigationRoutes.ProfileScreen) {
                            //navigator.navigate(NavigationRoutes.ProfileScreen.withArgs(0))
                            navigator.push(ProfileScreen(id = 0, navigator = navigator))
                        }
                    }
                    .size(iconsSize),
                tint = if(currentPage == NavigationRoutes.ProfileScreen) MaterialTheme.colorScheme.primary else Color.Black
            )
        }
    }
}