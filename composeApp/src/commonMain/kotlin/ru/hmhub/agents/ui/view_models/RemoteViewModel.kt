package ru.hmhub.agents.ui.view_models

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import ru.hmhub.agents.remote.RemoteApi
import ru.hmhub.agents.remote.serializables.Client
import ru.hmhub.agents.remote.serializables.Dealing
import ru.hmhub.agents.remote.serializables.EmployeeSerializable
import ru.hmhub.agents.ui.states.RemoteState

class RemoteViewModel(
    val api: RemoteApi
): ViewModel() {
    private val _employees = MutableStateFlow(emptyList<EmployeeSerializable>())
        .flatMapLatest { api.getEmployees() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _dealings = MutableStateFlow(emptyList<Dealing>())
        .flatMapLatest { api.getDealings() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _clients = MutableStateFlow(emptyList<Client>())
        .flatMapLatest { api.getClients() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _state = MutableStateFlow(RemoteState())
    val state = combine(_employees, _dealings, _clients, _state){ employeeSerializables, dealings, clients, remoteState ->
        remoteState.copy(
            employees = employeeSerializables,
            dealings = dealings,
            clients = clients
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), RemoteState())
}