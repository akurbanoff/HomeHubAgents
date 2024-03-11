package ru.hmhub.agents

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import org.koin.core.logger.Logger
import ru.hmhub.agents.remote.RemoteApi
import ru.hmhub.agents.remote.RemoteRepository
import ru.hmhub.agents.ui.screens.LoginScreen
import ru.hmhub.agents.ui.theme.HomeHubTheme
import ru.hmhub.agents.ui.theme.LocalThemeIsDark
import ru.hmhub.agents.ui.view_models.RemoteViewModel

@Composable
internal fun App() = HomeHubTheme {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val remoteApi = remember {RemoteApi()}
        val remoteRepository = remember { RemoteRepository(remoteApi) }
        val remoteViewModel = remember {RemoteViewModel(remoteRepository)}
        //val state by remoteViewModel.state.collectAsState()

        Navigator(LoginScreen(remoteViewModel = remoteViewModel), onBackPressed = {false}){
            SlideTransition(it, animationSpec = tween(durationMillis = 500, delayMillis = 100))
        }
    }
}

internal expect fun openUrl(url: String?)