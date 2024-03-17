package ru.hmhub.agents.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContentProviderCompat.requireContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.UUID

@Composable
actual fun convertBitmapToFile(imageBitmap: ImageBitmap) : File{
    val wrapper = ContextWrapper(LocalContext.current)
    var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
    file = File(file,"${UUID.randomUUID()}.jpg")
    val stream: OutputStream = FileOutputStream(file)
    imageBitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG,25,stream)
    stream.flush()
    stream.close()

    return file
}