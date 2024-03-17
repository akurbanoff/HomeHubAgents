package ru.hmhub.agents.utils

import androidx.compose.runtime.Composable

@Composable
expect fun BiometricAuth(onSuccess: () -> Unit, onError: () -> Unit)