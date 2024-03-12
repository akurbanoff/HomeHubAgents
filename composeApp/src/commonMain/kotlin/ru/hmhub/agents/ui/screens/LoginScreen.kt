package ru.hmhub.agents.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import homehubagents.composeapp.generated.resources.Res
import homehubagents.composeapp.generated.resources.ic_homehub
import homehubagents.composeapp.generated.resources.ic_homehub_main
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.data.remote.serializables.EmployeeSerializable
import ru.hmhub.agents.ui.screens.general_ui_elements.ElementDivider
import ru.hmhub.agents.ui.screens.general_ui_elements.ScreenError
import ru.hmhub.agents.ui.screens.general_ui_elements.ScreenLoading
import ru.hmhub.agents.ui.states.UiState
import ru.hmhub.agents.ui.view_models.RemoteViewModel

class LoginScreen(
    val remoteViewModel: RemoteViewModel
): Screen {

    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow
        val authState by remoteViewModel.authState.collectAsState()

        var userName = remember{ mutableStateOf("") }
        var password = remember{ mutableStateOf("") }
        val isPasswordCorrect = remember{ mutableStateOf(true) }
        var userId = remember { mutableStateOf(999) }
        val onLoginClicked = remember { mutableStateOf(false) }

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
                    isPasswordCorrect = isPasswordCorrect
                )
            }
        }
        if(onLoginClicked.value) {
            when (val state = authState.checkPasswordState) {
                is UiState.Error -> isPasswordCorrect.value = false
                is UiState.Loading -> ScreenLoading()
                is UiState.Success<*> -> remember{{
                    navigator.pop()
                    navigator.push(NewsScreen(navigator))
                }}
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
        isPasswordCorrect: MutableState<Boolean>
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
                isPasswordCorrect = isPasswordCorrect
            )
            ForgotPassword(modifier = Modifier.align(Alignment.End).padding(top = 16.dp))
            Spacer(modifier = Modifier.fillMaxHeight(0.25f))
            Button(
                onClick = {navigator.push(RegistrationScreen(
                    navigator = navigator,
                    remoteViewModel = remoteViewModel,
                    employeesList = employeesList
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
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.End)
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(topStart = 1000.dp, bottomStart = 1000.dp)
                    )
                    .padding(10.dp)
            )
        }
    }

    @Composable
    private fun ForgotPassword(modifier: Modifier = Modifier){
        Text(
            text = "forgot password?",
            modifier = modifier
        )
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
        isPasswordCorrect: MutableState<Boolean>
    ){
        var openExpandedMenu by remember { mutableStateOf(false) }
        var isPasswordVisible by remember { mutableStateOf(false) }

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
                        onValueChange = {openExpandedMenu = true},
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Gray,
                            unfocusedContainerColor = Color.Gray,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
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
                            DropdownMenuItem(
                                onClick = {
                                    userName.value = name
                                    userId.value = item.id
                                    openExpandedMenu = false
                                },
                                text = { Text(text = name) }
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
                    visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(mask = '*'),
                    isError = if(isPasswordCorrect.value) false else true,
                    supportingText = {
                        if(!isPasswordCorrect.value) Text("Вы ввели неправильный пароль")
                    },
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
                        focusedContainerColor = Color.Gray,
                        unfocusedContainerColor = Color.Gray,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        errorContainerColor = Color.Red,
                        errorIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(bottomEnd = 1000.dp, topStart = 0.dp, bottomStart = 0.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                )
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
                contentPadding = PaddingValues(8.dp)
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