package ru.hmhub.agents.ui.states

data class AuthState(
    val employeeState: UiState = UiState.Loading(),
    val insertPasswordState: UiState = UiState.Loading(),
    val checkPasswordState: UiState = UiState.Loading(),
    val checkPasswordExistState: UiState = UiState.Loading()
)
