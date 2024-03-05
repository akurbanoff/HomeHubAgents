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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
    BottomAppBar(
        containerColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                imageVector = Icons.Default.Newspaper,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (currentPage != NavigationRoutes.NewsScreen) {
                            navigator.push(NewsScreen(navigator = navigator))
                        }
                    }
                    .size(44.dp),
                tint = if(currentPage == NavigationRoutes.NewsScreen) MaterialTheme.colorScheme.primary else Color.Black
            )
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (currentPage != NavigationRoutes.AchievementsScreen) {
                            //navigator.navigate(NavigationRoutes.AchievementsScreen.route)
                            navigator.push(AchievementsScreen(navigator = navigator))
                        }
                    }
                    .size(44.dp),
                tint = if(currentPage == NavigationRoutes.AchievementsScreen) MaterialTheme.colorScheme.primary else Color.Black
            )
            Icon(
                imageVector = Icons.Default.Leaderboard,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (currentPage != NavigationRoutes.RatingScreen) {
                            //navigator.navigate(NavigationRoutes.RatingScreen.route)
                            navigator.push(RatingScreen(navigator = navigator))
                        }
                    }
                    .size(44.dp),
                tint = if(currentPage == NavigationRoutes.RatingScreen) MaterialTheme.colorScheme.primary else Color.Black
            )
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (currentPage != NavigationRoutes.ProfileScreen) {
                            //navigator.navigate(NavigationRoutes.ProfileScreen.withArgs(0))
                            navigator.push(ProfileScreen(id = 0, navigator = navigator))
                        }
                    }
                    .size(44.dp),
                tint = if(currentPage == NavigationRoutes.ProfileScreen) MaterialTheme.colorScheme.primary else Color.Black
            )
        }
    }
}