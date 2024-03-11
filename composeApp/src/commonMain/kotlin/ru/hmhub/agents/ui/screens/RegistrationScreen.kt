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
import ru.hmhub.agents.remote.serializables.EmployeeSerializable
import ru.hmhub.agents.ui.screens.general_ui_elements.ElementDivider
import ru.hmhub.agents.ui.view_models.RemoteViewModel

class RegistrationScreen(
    val navigator: Navigator,
    val remoteViewModel: RemoteViewModel
): Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var userName by remember{ mutableStateOf("") }
        var password by remember{ mutableStateOf("") }
        var checkerPassword by remember{ mutableStateOf("") }
        var isPasswordVisible by remember { mutableStateOf(false) }
        var isCheckerPasswordVisible by remember { mutableStateOf(false) }
        var userId by remember{ mutableStateOf(999) }
        var openUserNameExpandedMenu by remember { mutableStateOf(false) }
        val auth by remoteViewModel.authState.collectAsState()
        val state = emptyList<EmployeeSerializable>()

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
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
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
            Box(modifier = Modifier.fillMaxWidth()){
                Column {
                    ExposedDropdownMenuBox(
                        expanded = openUserNameExpandedMenu,
                        onExpandedChange = { openUserNameExpandedMenu = !openUserNameExpandedMenu },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                    ) {
                        TextField(
                            value = userName,
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
                            state.forEach { item ->
                                val name = "${item.last_name} ${item.first_name} ${item.middle_name}"
                                DropdownMenuItem(
                                    onClick = {
                                        userName = name
                                        userId = item.id
                                        openUserNameExpandedMenu = false
                                    },
                                    text = { Text(text = name) }
                                )
                            }
                        }
                    }
                    ElementDivider()
                    TextField(
                        value = password,
                        onValueChange = {password = it},
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
                                    .padding(end = 12.dp)
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
                        value = if(!isCheckerPasswordVisible) "*".repeat(checkerPassword.length) else checkerPassword,
                        onValueChange = { checkerPassword = it },
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
                                    .padding(end = 12.dp)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Gray,
                            unfocusedContainerColor = Color.Gray,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
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
                          //remoteViewModel.insertPassword(id = userId, password = password)
                          navigator.pop()
                          navigator.pop()
                          navigator.push(NewsScreen(navigator))
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
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))
            Button(
                onClick = {navigator.pop()}, //navigator.navigate(NavigationRoutes.RegistrationScreen.route) },
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
}