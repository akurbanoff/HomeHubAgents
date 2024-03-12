package ru.hmhub.agents.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import homehubagents.composeapp.generated.resources.Res
import homehubagents.composeapp.generated.resources.ic_homehub_main
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.data.remote.serializables.EmployeeSerializable
import ru.hmhub.agents.ui.screens.general_ui_elements.ElementDivider
import ru.hmhub.agents.ui.screens.general_ui_elements.ScreenError
import ru.hmhub.agents.ui.screens.general_ui_elements.ScreenLoading
import ru.hmhub.agents.ui.states.UiState
import ru.hmhub.agents.ui.view_models.RemoteViewModel

class RegistrationScreen(
    val navigator: Navigator,
    val remoteViewModel: RemoteViewModel,
    val employeesList: List<*>
): Screen {
    @Composable
    override fun Content() {
        var userName = remember{ mutableStateOf("") }
        var password = remember{ mutableStateOf("") }
        var checkerPassword = remember{ mutableStateOf("") }
        var userId = remember{ mutableStateOf(999) }
        val isInsertPasswordClicked = remember { mutableStateOf(false) }
        val errorMessage: MutableState<String?> = remember{ mutableStateOf(null) }
        val auth by remoteViewModel.authState.collectAsState()

        ScreenContent(
            navigator = navigator,
            userName = userName,
            userId = userId,
            password = password,
            checkerPassword = checkerPassword,
            employeesList = employeesList,
            onInsertPassword = { remoteViewModel.insertPassword(id = userId.value, password = password.value) },
            errorMessage = errorMessage,
            isInsertPasswordClicked = isInsertPasswordClicked
        )

        if(isInsertPasswordClicked.value) {
            when (val state = auth.insertPasswordState) {
                is UiState.Error -> errorMessage.value = state.throwable.message
                is UiState.Loading -> ScreenLoading()
                is UiState.Success<*> -> {
                    navigator.pop()
                    navigator.pop()
                    navigator.push(NewsScreen(navigator))
                }
            }
        }

    }

    @Composable
    private fun ScreenContent(
        navigator: Navigator,
        userName: MutableState<String>,
        userId: MutableState<Int>,
        password: MutableState<String>,
        checkerPassword: MutableState<String>,
        employeesList: List<*>,
        onInsertPassword: () -> Unit,
        errorMessage: MutableState<String?>,
        isInsertPasswordClicked: MutableState<Boolean>
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Header()
            if(errorMessage.value != null){
                Box(
                    modifier = Modifier.fillMaxHeight(0.15f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = errorMessage.value ?: "Что-то пошло не так",
                        color = Color.Red,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            } else {
                Spacer(modifier = Modifier.fillMaxHeight(0.15f))
            }
            Body(
                navigator = navigator,
                userName = userName,
                userId = userId,
                checkerPassword = checkerPassword,
                password = password,
                employeesList = employeesList,
                onInsertPassword = onInsertPassword,
                isInsertPasswordClicked = isInsertPasswordClicked
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))
            BackButton(navigator)
        }
    }

    @Composable
    private fun Header(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_homehub_main),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Text(
                text = "Регистрация",
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Body(
        navigator: Navigator,
        userName: MutableState<String>,
        userId: MutableState<Int>,
        password: MutableState<String>,
        checkerPassword: MutableState<String>,
        employeesList: List<*>,
        onInsertPassword: () -> Unit,
        isInsertPasswordClicked: MutableState<Boolean>
    ){
        var openUserNameExpandedMenu by remember { mutableStateOf(false) }
        var isPasswordVisible by remember { mutableStateOf(false) }
        var isCheckerPasswordVisible by remember { mutableStateOf(false) }
        var isPasswordsMatches by remember { mutableStateOf(true) }


        Box(modifier = Modifier.fillMaxWidth()){
            Column {
                ExposedDropdownMenuBox(
                    expanded = openUserNameExpandedMenu,
                    onExpandedChange = { openUserNameExpandedMenu = !openUserNameExpandedMenu },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                ) {
                    TextField(
                        value = userName.value,
                        readOnly = true,
                        onValueChange = {openUserNameExpandedMenu = true},
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
//                            trailingIcon = {
//                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = openExpandedMenu)
//                            },
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
                        expanded = openUserNameExpandedMenu,
                        onDismissRequest = { openUserNameExpandedMenu = false }
                    ){
                        employeesList.forEach { item ->
                            item as EmployeeSerializable
                            val name = "${item.last_name} ${item.first_name} ${item.middle_name}"
                            DropdownMenuItem(
                                onClick = {
                                    userName.value = name
                                    userId.value = item.id
                                    openUserNameExpandedMenu = false
                                },
                                text = { Text(text = name) }
                            )
                        }
                    }
                }
                ElementDivider()
                TextField(
                    value = password.value,
                    onValueChange = {password.value = it},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            modifier = Modifier
                                .size(44.dp)
                                .padding(start = 8.dp)
                        )
                    },
                    visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(mask = '*'),
                    trailingIcon = {
                        Icon(
                            imageVector = if(!isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                isPasswordVisible = !isPasswordVisible
                            }
                                .padding(end = 45.dp)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Gray,
                        unfocusedContainerColor = Color.Gray,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                )
                ElementDivider()
                TextField(
                    value = checkerPassword.value,
                    onValueChange = { checkerPassword.value = it },
                    isError = !isPasswordsMatches,
                    supportingText = {
                         if(!isPasswordsMatches) Text("Пароли несовпадают")
                    },
                    visualTransformation = if(isCheckerPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(mask = '*'),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            modifier = Modifier
                                .size(44.dp)
                                .padding(start = 8.dp)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = if(!isCheckerPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                isCheckerPasswordVisible = !isCheckerPasswordVisible
                            }
                                .padding(end = 45.dp)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Gray,
                        unfocusedContainerColor = Color.Gray,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        errorContainerColor = Color.Red
                    ),
                    shape = RoundedCornerShape(
                        bottomEnd = 1000.dp,
                        topStart = 0.dp,
                        bottomStart = 0.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                )
            }
            Button(
                onClick = {
                    if(password.value == checkerPassword.value){
                        isPasswordsMatches = true
                        isInsertPasswordClicked.value = true
                        onInsertPassword()
                    } else {
                        isPasswordsMatches = false
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(90.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowRightAlt,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }

    @Composable
    private fun BackButton(
        navigator: Navigator
    ){
        Button(
            onClick = {navigator.pop()},
            shape = RoundedCornerShape(topEnd = 1000.dp, bottomEnd = 1000.dp, topStart = 0.dp, bottomStart = 0.dp),
            modifier = Modifier.size(height = 80.dp, width = 200.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(44.dp)
                )
                Text(
                    text = "Вход",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}