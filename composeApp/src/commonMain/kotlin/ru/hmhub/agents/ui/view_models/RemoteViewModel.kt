package ru.hmhub.agents.ui.view_models

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.hmhub.agents.remote.RemoteRepository
import ru.hmhub.agents.ui.states.AuthState
import ru.hmhub.agents.ui.states.UiState

class RemoteViewModel(
    val repository: RemoteRepository
): ViewModel() {
//    private val _employees = MutableStateFlow(UiState.Loading())
//        .flatMapLatest { repository.getEmployees() }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading())
//
//    private val _dealings = MutableStateFlow(emptyList<Dealing>())
//        .flatMapLatest { repository.getDealings() }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading())
//
    private val _employeeState = MutableStateFlow<UiState>(UiState.Loading())
    val employeeState = _employeeState.asStateFlow()

    private val _dealingState = MutableStateFlow<UiState>(UiState.Loading())
    val dealingState = _dealingState.asStateFlow()

    private val _insertPasswordState = MutableStateFlow<UiState>(UiState.Loading())
    val insertPasswordState = _insertPasswordState.asStateFlow()

    private val _checkPasswordState = MutableStateFlow<UiState>(UiState.Loading())
    val checkPasswordState = _checkPasswordState.asStateFlow()

    private val _authState = MutableStateFlow(AuthState())
    val authState = combine(_employeeState, _insertPasswordState, _checkPasswordState, _authState){ employee, insertPassword, checkPassword, auth ->
        auth.copy(
            employeeState = employee,
            insertPasswordState = insertPassword,
            checkPasswordState = checkPassword
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AuthState())

//    private val _clients = MutableStateFlow(emptyList<Client>())
//        .flatMapLatest { api.getClients() }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

//    private val _state = MutableStateFlow(RemoteState())
//    val state = combine(_employees, _dealings, _state){ employeeSerializables, dealings, remoteState ->
//        remoteState.copy(
//            employees = employeeSerializables,
//            dealings = dealings
//        )
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), RemoteState())
    fun getEmployees(){
        viewModelScope.launch(Dispatchers.IO) {
            _employeeState.value = UiState.Loading()
            repository.getEmployees().collect{ result ->
                _employeeState.value = result
            }
        }
    }

    fun getDealings(){
        viewModelScope.launch(Dispatchers.IO) {
            _dealingState.value = UiState.Loading()
            repository.getDealings().collect{ result ->
                _dealingState.value = result
            }
        }
    }

    fun insertPassword(id: Int, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            _insertPasswordState.value = UiState.Loading()
            repository.insertPassword(id = id, password = password).collect{ result ->
                _insertPasswordState.value = result
            }
        }
    }

    fun checkPassword(id: Int, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            _checkPasswordState.value = UiState.Loading()
            repository.checkPassword(id, password).collect{ result ->
                _checkPasswordState.value = result
            }
        }
    }
}