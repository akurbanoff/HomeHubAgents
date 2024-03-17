package ru.hmhub.agents.utils

import androidx.compose.runtime.Composable

@Composable
expect fun rememberGalleryManager(
    onResult: (SharedImage?) -> Unit
) : GalleryManager

expect class GalleryManager(
    onLaunch: () -> Unit
){
    fun launch()
}

expect class PermissionsManager(callback: PermissionCallback) : PermissionHandler

interface PermissionCallback {
    fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus)
}

@Composable
expect fun createPermissionsManager(callback: PermissionCallback): PermissionsManager

interface PermissionHandler {
    @Composable
    fun askPermission(permission: PermissionType)

    @Composable
    fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    fun launchSettings()

}

enum class PermissionType {
    GALLERY
}

enum class PermissionStatus {
    GRANTED,
    DENIED,
    SHOW_RATIONAL
}