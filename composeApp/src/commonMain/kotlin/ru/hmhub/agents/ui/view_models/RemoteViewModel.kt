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
import ru.hmhub.agents.data.pagingSources.PagingRepository
import ru.hmhub.agents.data.remote.RemoteRepository
import ru.hmhub.agents.ui.states.AuthState
import ru.hmhub.agents.ui.states.UiState

class RemoteViewModel(
    private val remoteRepository: RemoteRepository,
    private val pagingRepository: PagingRepository
): ViewModel() {

    init {
        getEmployees()
    }

    private val _employeeState = MutableStateFlow<UiState>(UiState.Loading())
    val employeeState = _employeeState.asStateFlow()

//    private val _dealingState = MutableStateFlow<UiState>(UiState.Loading())
//    val dealingState = _dealingState.asStateFlow()

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

    fun getEmployees(){
        viewModelScope.launch(Dispatchers.IO) {
            _employeeState.value = UiState.Loading()
            remoteRepository.getEmployees().collect{ result ->
                _employeeState.value = result
            }
        }
    }

//    fun getDealings(skip: Int = 0){
//        viewModelScope.launch(Dispatchers.IO) {
//            _dealingState.value = UiState.Loading()
//            repository.getDealings(skip).collect{ result ->
//                _dealingState.value = result
//            }
//        }
//    }

    fun insertPassword(id: Int, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            _insertPasswordState.value = UiState.Loading()
            remoteRepository.insertPassword(id = id, password = password).collect{ result ->
                _insertPasswordState.value = result
            }
        }
    }

    fun checkPassword(id: Int, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            _checkPasswordState.value = UiState.Loading()
            remoteRepository.checkPassword(id, password).collect{ result ->
                _checkPasswordState.value = result
            }
        }
    }
}