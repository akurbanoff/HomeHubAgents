package ru.hmhub.agents.ui.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import com.seiko.imageloader.rememberImagePainter
import homehubagents.composeapp.generated.resources.Res
import homehubagents.composeapp.generated.resources.ic_homehub
import homehubagents.composeapp.generated.resources.ic_homehub_main
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.data.in_memory.InMemoryHelper
import ru.hmhub.agents.data.remote.serializables.EmployeeSerializable
import ru.hmhub.agents.ui.screens.general_ui_elements.ElementDivider
import ru.hmhub.agents.ui.screens.general_ui_elements.ScreenError
import ru.hmhub.agents.ui.screens.general_ui_elements.ScreenLoading
import ru.hmhub.agents.ui.states.UiState
import ru.hmhub.agents.ui.view_models.RemoteViewModel
import ru.hmhub.agents.utils.BiometricAuth
import kotlin.random.Random

class LoginScreen(
    val remoteViewModel: RemoteViewModel,
    val inMemoryHelper: InMemoryHelper
): Screen {

    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow
        val authState by remoteViewModel.authState.collectAsState()

        var userName = rememberSaveable{ mutableStateOf("") }
        var password = rememberSaveable{ mutableStateOf("") }
        val isPasswordCorrect = rememberSaveable{ mutableStateOf(true) }
        var userId = rememberSaveable { mutableStateOf(999) }
        val onLoginClicked = rememberSaveable { mutableStateOf(false) }
        val currentUser = remember { mutableListOf<EmployeeSerializable>() }
        var countSuccessTimes by rememberSaveable { mutableStateOf(0) }
        val biometricAuthEnable = rememberSaveable { mutableStateOf(false) }
        val rememberMe = rememberSaveable { mutableStateOf(inMemoryHelper.getUserId() != 999) }

        if(biometricAuthEnable.value && currentUser.firstOrNull() != null){
            BiometricAuth(
                onSuccess = {
                    if(countSuccessTimes < 1){
                        countSuccessTimes += 1
                        inMemoryHelper.saveUserId(userId.value)
                        navigator.popAll()
                        navigator.push(NewsScreen(inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel))
                    }
                },
                onError = {
                    biometricAuthEnable.value = false
                }
            )
        }

        when(val state = authState.employeeState){
            is UiState.Error -> ScreenError(error = state.throwable, onRefresh = { remoteViewModel.getEmployees() })
            is UiState.Loading -> ScreenLoading()
            is UiState.Success<*> -> {
                LoginContent(
                    navigator = navigator,
                    employeesList = state.result as List<*>,
                    onLogin = { remoteViewModel.checkPassword(id = userId.value, password = password.value) },
                    userName = userName,
                    userId = userId,
                    password = password,
                    onLoginClicked = onLoginClicked,
                    isPasswordCorrect = isPasswordCorrect,
                    rememberMe = rememberMe,
                    currentUser = currentUser,
                    biometricAuthEnable = biometricAuthEnable
                )
            }
        }
        if(onLoginClicked.value) {
            when (val state = authState.checkPasswordState) {
                is UiState.Error -> isPasswordCorrect.value = false
                is UiState.Loading -> ScreenLoading()
                is UiState.Success<*> -> {
                    if(countSuccessTimes < 1){
                        countSuccessTimes += 1
                        if(rememberMe.value && currentUser.firstOrNull() != null){
                            inMemoryHelper.saveUserId(userId.value)
                            inMemoryHelper.saveUser(currentUser.first())
                        } else if(!rememberMe.value){
                            inMemoryHelper.deleteUserId()
                            inMemoryHelper.saveUser(currentUser.first())
                        }
                        navigator.popAll()
                        navigator.push(NewsScreen(inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel))
                    }
                }
            }
        }
    }

    @Composable
    private fun LoginContent(
        navigator: Navigator,
        employeesList: List<*>,
        onLogin: () -> Unit,
        userName: MutableState<String>,
        userId: MutableState<Int>,
        password: MutableState<String>,
        onLoginClicked: MutableState<Boolean>,
        isPasswordCorrect: MutableState<Boolean>,
        rememberMe: MutableState<Boolean>,
        currentUser: MutableList<EmployeeSerializable>,
        biometricAuthEnable: MutableState<Boolean>
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Header()
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
            LoginBody(
                navigator = navigator,
                employeesList = employeesList,
                onLogin = onLogin,
                userId = userId,
                userName = userName,
                password = password,
                onLoginClicked = onLoginClicked,
                isPasswordCorrect = isPasswordCorrect,
                currentUser = currentUser
            )
            Helper(
                modifier = Modifier.padding(top = 16.dp),
                rememberMe = rememberMe
            )
            Icon(
                imageVector = Icons.Default.Fingerprint,
                contentDescription = null,
                modifier = Modifier.clickable {
                    biometricAuthEnable.value = true
                }.align(Alignment.End).size(44.dp)
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
            Button(
                onClick = {navigator.push(RegistrationScreen(
                    navigator = navigator,
                    remoteViewModel = remoteViewModel,
                    employeesList = employeesList,
                    inMemoryHelper = inMemoryHelper
                ))},
                shape = RoundedCornerShape(topEnd = 1000.dp, bottomEnd = 1000.dp, topStart = 0.dp, bottomStart = 0.dp),
                modifier = Modifier.height(80.dp) // width = 200.dp
            ) {
                Text(
                    text = "Регистрация",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    //modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    @Composable
    private fun Header(modifier: Modifier = Modifier){
        Column(
            modifier = modifier
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_homehub_main),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Text(
                text = "Вход",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.End)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(topStart = 1000.dp, bottomStart = 1000.dp)
                    )
                    .padding(10.dp)
            )
        }
    }

    @Composable
    private fun Helper(modifier: Modifier = Modifier, rememberMe: MutableState<Boolean>){
        Row(
            modifier = modifier.fillMaxWidth().padding(start = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberMe.value,
                    onCheckedChange = {
                        rememberMe.value = !rememberMe.value
                                      },
                    colors = CheckboxDefaults.colors()
                )
                Text("запомнить меня")
            }
            Text(
                text = "забыл пароль?"
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun LoginBody(
        modifier: Modifier = Modifier,
        navigator: Navigator,
        employeesList: List<*>,
        onLogin: () -> Unit,
        userName: MutableState<String>,
        userId: MutableState<Int>,
        password: MutableState<String>,
        onLoginClicked: MutableState<Boolean>,
        isPasswordCorrect: MutableState<Boolean>,
        currentUser: MutableList<EmployeeSerializable>
    ){
        var openExpandedMenu by rememberSaveable { mutableStateOf(false) }
        var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

        Box(modifier = modifier.fillMaxWidth()) {
            Column {
                ExposedDropdownMenuBox(
                    expanded = openExpandedMenu,
                    onExpandedChange = { openExpandedMenu = !openExpandedMenu },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                ) {
                    TextField(
                        value = userName.value,
                        readOnly = true,
                        label = {Text(text = "Агент")},
                        onValueChange = {openExpandedMenu = true},
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            focusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(44.dp).padding(start = 8.dp)
                            )
                        },
                        shape = RoundedCornerShape(
                            topEnd = 1000.dp,
                            topStart = 0.dp,
                            bottomStart = 0.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = openExpandedMenu,
                        onDismissRequest = { openExpandedMenu = false }
                    ){
                        employeesList.forEach { item ->
                            item as EmployeeSerializable
                            val name = "${item.last_name} ${item.first_name} ${item.middle_name}"
                            currentUser.clear()
                            currentUser.add(item)
                            //inMemoryHelper.saveUser(item)
                            DropdownMenuItem(
                                onClick = {
                                    userName.value = name
                                    userId.value = item.id
                                    openExpandedMenu = false
                                },
                                text = { Text(text = name) },
                                leadingIcon = {
                                    Image(
                                        painter = rememberImagePainter(item.photos?.first() ?: ""),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(CircleShape)
                                    )
                                }
                            )
                        }
                    }
                }
                ElementDivider()
                TextField(
                    value = password.value,
                    onValueChange = {
                        password.value = it
                        isPasswordCorrect.value = true
                    },
                    label = {Text(text = "Пароль")},
                    visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(mask = '*'),
                    isError = if(isPasswordCorrect.value) false else true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            modifier = Modifier.size(44.dp).padding(start = 8.dp)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = if(isPasswordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                isPasswordVisible = !isPasswordVisible
                            }
                                .padding(end = 40.dp)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                        errorIndicatorColor = Color.Transparent,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = RoundedCornerShape(bottomEnd = 1000.dp, topStart = 0.dp, bottomStart = 0.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                )
                if(!isPasswordCorrect.value) Text("Вы ввели неправильный пароль", color = MaterialTheme.colorScheme.error)
            }
            Button(
                onClick = {
                    onLoginClicked.value = true
                    onLogin()
                          },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(70.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowRightAlt,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}