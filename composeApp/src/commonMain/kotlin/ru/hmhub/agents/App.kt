package ru.hmhub.agents

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import ru.hmhub.agents.data.in_memory.InMemoryHelper
import ru.hmhub.agents.data.pagingSources.PagingRepositoryImpl
import ru.hmhub.agents.data.remote.api.RemoteApi
import ru.hmhub.agents.data.remote.RemoteRepository
import ru.hmhub.agents.data.remote.RemoteRepositoryImpl
import ru.hmhub.agents.data.remote.serializables.EmployeeSerializable
import ru.hmhub.agents.ui.screens.LoginScreen
import ru.hmhub.agents.ui.screens.NewsScreen
import ru.hmhub.agents.ui.screens.general_ui_elements.ScreenError
import ru.hmhub.agents.ui.screens.general_ui_elements.ScreenLoading
import ru.hmhub.agents.ui.states.UiState
import ru.hmhub.agents.ui.theme.HomeHubTheme
import ru.hmhub.agents.ui.view_models.RemoteViewModel
import ru.hmhub.agents.utils.SharedPreferencesManager

@Composable
internal fun App(
    sharedPreferencesManager: SharedPreferencesManager
) = HomeHubTheme {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val remoteApi = remember { RemoteApi() }
        val remoteRepository = remember { RemoteRepositoryImpl(remoteApi) }
        val pagingRepository = remember { PagingRepositoryImpl(remoteApi) }
        val remoteViewModel = remember {RemoteViewModel(remoteRepository = remoteRepository, pagingRepository = pagingRepository)}
        val inMemoryHelper = remember { InMemoryHelper(sharedPreferencesManager) }

        val startScreen = if(inMemoryHelper.getUserId() != 999){
            val currentUser = inMemoryHelper.getCurrentUser()
            val employees by remoteViewModel.employeeState.collectAsState()

            if(currentUser == null){
                when(val state = employees){
                    is UiState.Error -> ScreenError(error = state.throwable, onRefresh = {  })
                    is UiState.Loading -> ScreenLoading()
                    is UiState.Success<*> -> {
                        val list = state.result as List<*>
                        list.forEach { item ->
                            if(item is EmployeeSerializable){
                                if(item.id == inMemoryHelper.getUserId()) inMemoryHelper.saveUser(item)
                            }
                        }
                    }
                }
            }

            NewsScreen(inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel)
        } else {
            LoginScreen(remoteViewModel = remoteViewModel, inMemoryHelper = inMemoryHelper)
        }

        Navigator(startScreen, onBackPressed = {false}){
            SlideTransition(it, animationSpec = tween(durationMillis = 500, delayMillis = 100))
        }
    }
}

internal expect fun openUrl(url: String?)