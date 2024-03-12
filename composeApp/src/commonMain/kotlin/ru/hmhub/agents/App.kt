package ru.hmhub.agents

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import ru.hmhub.agents.data.pagingSources.PagingRepositoryImpl
import ru.hmhub.agents.data.remote.api.RemoteApi
import ru.hmhub.agents.data.remote.RemoteRepository
import ru.hmhub.agents.data.remote.RemoteRepositoryImpl
import ru.hmhub.agents.ui.screens.LoginScreen
import ru.hmhub.agents.ui.theme.HomeHubTheme
import ru.hmhub.agents.ui.view_models.RemoteViewModel

@Composable
internal fun App() = HomeHubTheme {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val remoteApi = remember { RemoteApi() }
        val remoteRepository = remember { RemoteRepositoryImpl(remoteApi) }
        val pagingRepository = remember { PagingRepositoryImpl(remoteApi) }
        val remoteViewModel = remember {RemoteViewModel(remoteRepository = remoteRepository, pagingRepository = pagingRepository)}

        Navigator(LoginScreen(remoteViewModel = remoteViewModel), onBackPressed = {false}){
            SlideTransition(it, animationSpec = tween(durationMillis = 500, delayMillis = 100))
        }
    }
}

internal expect fun openUrl(url: String?)