package ru.hmhub.agents.utils

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import android.provider.Settings

@Composable
actual fun rememberGalleryManager(
    onResult: (SharedImage?) -> Unit
) : GalleryManager {
    val context = LocalContext.current
    val contentResolver: ContentResolver = context.contentResolver
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let{
            onResult.invoke(SharedImage(BitmapUtils.getBitmapFromUri(uri, contentResolver)))
        }
    }
    return remember {
        GalleryManager(onLaunch = {
            galleryLauncher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        })
    }
}

actual class GalleryManager actual constructor(private val onLaunch: () -> Unit) {
    actual fun launch() {
        onLaunch()
    }
}

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return remember { PermissionsManager(callback) }
}

actual class PermissionsManager actual constructor(private val callback: PermissionCallback) : PermissionHandler {
    @Composable
    override fun askPermission(permission: PermissionType) {
        val lifecycleOwner = LocalLifecycleOwner.current

        when (permission) {
            PermissionType.GALLERY -> {
                // Granted by default because in Android GetContent API does not require any
                // runtime permissions, and i am using it to access gallery in my app
                callback.onPermissionStatus(
                    permission, PermissionStatus.GRANTED
                )
            }
        }
    }


    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.GALLERY -> {
                // Granted by default because in Android GetContent API does not require any
                // runtime permissions, and i am using it to access gallery in my app
                true
            }
        }
    }

    @Composable
    override fun launchSettings() {
        val context = LocalContext.current
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        ).also {
            context.startActivity(it)
        }
    }
}