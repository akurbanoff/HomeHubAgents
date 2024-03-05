package ru.hmhub.agents.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import homehubagents.composeapp.generated.resources.Res
import homehubagents.composeapp.generated.resources.ic_homehub_main
import org.jetbrains.compose.resources.painterResource

class RegistrationScreen(
    val navigator: Navigator
): Screen {
    @Composable
    override fun Content() {
        var userName by remember{ mutableStateOf("") }
        var password by remember{ mutableStateOf("") }
        var email by remember{ mutableStateOf("") }

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
                text = "REGISTER",
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
                //.height(70.dp)
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
            Box(modifier = Modifier.fillMaxWidth()){
                Column {
                    TextField(
                        value = userName,
                        onValueChange = {userName = it},
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(44.dp)
                                    .padding(start = 8.dp)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Gray,
                            unfocusedContainerColor = Color.Gray,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(topEnd = 1000.dp, topStart = 0.dp, bottomStart = 0.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(70.dp)
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.fillMaxWidth(0.9f))
                        Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.fillMaxWidth(0.7f))
                    }
                    TextField(
                        value = "*".repeat(password.length),
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
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Gray,
                            unfocusedContainerColor = Color.Gray,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(0.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(70.dp)
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.fillMaxWidth(0.9f))
                        Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.fillMaxWidth(0.7f))
                    }
                    TextField(
                        value = email,
                        onValueChange = {email = it},
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Mail,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(44.dp)
                                    .padding(start = 8.dp)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Gray,
                            unfocusedContainerColor = Color.Gray,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(bottomEnd = 1000.dp, topStart = 0.dp, bottomStart = 0.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(70.dp)
                    )
                }
                Button(
                    onClick = {
//                        navigator.popBackStack()
//                        navigator.popBackStack()
//                        navigator.navigate(NavigationRoutes.NewsScreen.route)
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
        }
    }
}