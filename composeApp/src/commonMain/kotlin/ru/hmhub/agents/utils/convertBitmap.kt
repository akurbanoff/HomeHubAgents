package ru.hmhub.agents.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import io.kamel.core.utils.File

@Composable
expect fun convertBitmapToFile(imageBitmap: ImageBitmap) : File