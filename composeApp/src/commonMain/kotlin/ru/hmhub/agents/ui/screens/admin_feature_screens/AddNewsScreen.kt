package ru.hmhub.agents.ui.screens.admin_feature_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Backup
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.seiko.imageloader.rememberImagePainter
import homehubagents.composeapp.generated.resources.Res
import homehubagents.composeapp.generated.resources.ic_homehub
import io.kamel.core.utils.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import ru.hmhub.agents.data.in_memory.InMemoryHelper
import ru.hmhub.agents.ui.screens.general_ui_elements.DefaultTopAppBar
import ru.hmhub.agents.ui.view_models.RemoteViewModel
import ru.hmhub.agents.utils.PermissionCallback
import ru.hmhub.agents.utils.PermissionStatus
import ru.hmhub.agents.utils.PermissionType
import ru.hmhub.agents.utils.convertBitmapToFile
import ru.hmhub.agents.utils.createPermissionsManager
import ru.hmhub.agents.utils.rememberGalleryManager

class AddNewsScreen(
    val navigator: Navigator,
    val inMemoryHelper: InMemoryHelper,
    val remoteViewModel: RemoteViewModel
) : Screen {
    @Composable
    override fun Content() {
        val title = remember { mutableStateOf("") }
        val isTitleBiggerThanExpected = remember { mutableStateOf(false) }

        var showErrorMessage by remember { mutableStateOf(false) }

        val description = remember { mutableStateOf("") }

        val coroutineScope = rememberCoroutineScope()
        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
        var newImageAdded by remember { mutableStateOf(false) }
        var launchGallery by remember { mutableStateOf(value = false) }
        var permissionRationalDialog by remember { mutableStateOf(value = false) }

        var imagesList = remember { mutableStateOf(mutableListOf<ImageBitmap>()) }//remember { mutableListOf<ImageBitmap>() }
        val convertedImages = remember { mutableListOf<File>() }

        println(imagesList.value)

        val permissionsManager = createPermissionsManager(object : PermissionCallback {
            override fun onPermissionStatus(
                permissionType: PermissionType,
                status: PermissionStatus
            ) {
                when (status) {
                    PermissionStatus.GRANTED -> {
                        when (permissionType) {
                            PermissionType.GALLERY -> launchGallery = true
                        }
                    }

                    else -> {
                        permissionRationalDialog = true
                    }
                }
            }
        })

        val galleryManager = rememberGalleryManager {
            coroutineScope.launch {
                val bitmap = withContext(Dispatchers.Default) {
                    it?.toImageBitmap()
                }
                imageBitmap = bitmap
                imagesList.value.add(imageBitmap!!)
                newImageAdded = true
            }
        }

        if(launchGallery){
            galleryManager.launch()
            launchGallery = false
        }

        if(newImageAdded){
            convertedImages.add(convertBitmapToFile(imageBitmap!!))
            println(convertedImages[0])
            newImageAdded = false
        }


//        if (launchGallery) {
//            if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
//                galleryManager.launch()
//            } else {
//                permissionsManager.askPermission(PermissionType.GALLERY)
//            }
//            launchGallery = false
//        }

        Scaffold(
            topBar = { DefaultTopAppBar(title = "Создать Новость", navigator = navigator, inMemoryHelper = inMemoryHelper, remoteViewModel = remoteViewModel) },
            modifier = Modifier.padding(16.dp)
        ){padding ->
            LazyColumn (
                modifier = Modifier.padding(padding)
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                launchGallery = true
                            },
                        shape = MaterialTheme.shapes.large
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Backup,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Загрузить изображение",
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                    LazyRow(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp)
                    ) {
                        items(imagesList.value) { currentImage ->
                            Box(
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Image(
                                    bitmap = currentImage,
                                    contentDescription = null,
                                    modifier = Modifier.height(180.dp),
                                    contentScale = ContentScale.FillHeight
                                )
                                Icon(
                                    imageVector = Icons.Outlined.Cancel,
                                    contentDescription = null,
                                    tint = Color.Red,
                                    modifier = Modifier.size(30.dp).align(Alignment.TopEnd)
                                        .clickable {
                                            //convertedImages.removeAt(imagesList.indexOf(currentImage))
                                            imagesList.value.remove(currentImage)
                                        }
                                )
                            }
                        }
                    }
                    OutlinedTextField(
                        value = title.value,
                        onValueChange = {
                            title.value = it
                            if (title.value.length >= 200)
                                isTitleBiggerThanExpected.value = true
                            else {
                                isTitleBiggerThanExpected.value = false
                                showErrorMessage = false
                            }
                        },
                        label = { Text(text = "Заголовок") },
                        isError = isTitleBiggerThanExpected.value,
                        shape = MaterialTheme.shapes.medium,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Title,
                                contentDescription = null
                            )
                        },
                        supportingText = {
                            Text("Максимальное количество символов - ${title.value.length}/200")
                        },
                        modifier = Modifier.fillMaxWidth().heightIn(30.dp)
                    )
                    OutlinedTextField(
                        value = description.value,
                        onValueChange = {
                            description.value = it
                        },
                        label = { Text("Описание") },
                        shape = MaterialTheme.shapes.medium,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Description,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth().heightIn(130.dp).padding(top = 8.dp)
                    )
                    if(showErrorMessage) {
                        Text(
                            text = "Слишком длинный заголовок, уменьшите количество символов до 200",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(
                            onClick = { navigator.pop() },
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            Text("Назад")
                        }
                        Button(
                            onClick = {
                                if(isTitleBiggerThanExpected.value){
                                    showErrorMessage = true
                                } else {
                                    remoteViewModel.createNews(
                                        title = title.value,
                                        description = description.value,
                                        photos = convertedImages.toList()
                                    )
                                }
                            }
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Text("Создать")
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

