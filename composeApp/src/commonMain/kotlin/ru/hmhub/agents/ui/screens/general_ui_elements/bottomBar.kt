package ru.hmhub.agents.ui.screens.general_ui_elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import ru.hmhub.agents.data.in_memory.InMemoryHelper
import ru.hmhub.agents.ui.navigation.NavigationRoutes
import ru.hmhub.agents.ui.screens.AchievementsScreen
import ru.hmhub.agents.ui.screens.NewsScreen
import ru.hmhub.agents.ui.screens.ProfileScreen
import ru.hmhub.agents.ui.screens.RatingScreen
import ru.hmhub.agents.ui.view_models.RemoteViewModel

@Composable
fun DefaultBottomBar(
    navigator: Navigator,
    currentPage: NavigationRoutes,
    inMemoryHelper: InMemoryHelper,
    remoteViewModel: RemoteViewModel
){
    val iconsSize = 44.dp
    var isAppsIconClicked by rememberSaveable { mutableStateOf(false) }

//    val //сделать функцию получения конкретного юзера по id взятым из inMemoryHelper в bottomBar

    NavigationBar(
        containerColor = Color.Transparent,
        modifier = Modifier.height(100.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if(currentPage == NavigationRoutes.NewsScreen) Icons.Filled.Newspaper else Icons.Outlined.Newspaper,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (currentPage != NavigationRoutes.NewsScreen) {
                            navigator.pop()
                            navigator.push(NewsScreen(inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel))
                        }
                    }
                    .size(iconsSize),
                tint = if(currentPage == NavigationRoutes.NewsScreen) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            )
            Icon(
                imageVector = if(currentPage == NavigationRoutes.AchievementsScreen) Icons.Filled.EmojiEvents else Icons.Outlined.EmojiEvents,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (currentPage != NavigationRoutes.AchievementsScreen) {
                            navigator.pop()
                            navigator.push(AchievementsScreen(navigator = navigator, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel))
                        }
                    }
                    .size(iconsSize),
                tint = if(currentPage == NavigationRoutes.AchievementsScreen) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            )
            Icon(
                imageVector = Icons.Outlined.Apps,
                contentDescription = null,
                modifier = Modifier
                    .clickable { isAppsIconClicked = !isAppsIconClicked }
                    .size(iconsSize),
                tint = if(isAppsIconClicked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            )
            Icon(
                imageVector = if(currentPage == NavigationRoutes.RatingScreen) Icons.Filled.Leaderboard else Icons.Outlined.Leaderboard,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (currentPage != NavigationRoutes.RatingScreen) {
                            navigator.pop()
                            navigator.push(RatingScreen(navigator = navigator, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel))
                        }
                    }
                    .size(iconsSize),
                tint = if(currentPage == NavigationRoutes.RatingScreen) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            )
            Icon(
                imageVector = if(currentPage == NavigationRoutes.ProfileScreen) Icons.Filled.Person else Icons.Outlined.Person,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (currentPage != NavigationRoutes.ProfileScreen) {
                            navigator.pop()
                            navigator.push(ProfileScreen(id = inMemoryHelper.getUserId(), navigator = navigator, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel))
                        }
                    }
                    .size(iconsSize),
                tint = if(currentPage == NavigationRoutes.ProfileScreen) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            )
        }
    }
}