package ru.hmhub.agents.ui.view_models

import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.kamel.core.utils.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.hmhub.agents.data.pagingSources.PagingRepository
import ru.hmhub.agents.data.remote.RemoteRepository
import ru.hmhub.agents.data.remote.responseSerializables.NewsSerializable
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

    private val _checkPasswordExistState = MutableStateFlow<UiState>(UiState.Loading())

    private val _authState = MutableStateFlow(AuthState())
    val authState = combine(_employeeState, _insertPasswordState, _checkPasswordState, _checkPasswordExistState, _authState){ employee, insertPassword, checkPassword, checkPasswordExist, auth ->
        auth.copy(
            employeeState = employee,
            insertPasswordState = insertPassword,
            checkPasswordState = checkPassword,
            checkPasswordExistState = checkPasswordExist
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AuthState())

    private val _insertNewsState = MutableStateFlow<UiState>(UiState.Loading())
    val insertNewsState = _insertNewsState.asStateFlow()

    fun getNews() : Flow<PagingData<NewsSerializable>> = remoteRepository.getNews().cachedIn(viewModelScope)

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

    fun checkPasswordExist(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _checkPasswordExistState.value = UiState.Loading()
            remoteRepository.checkPasswordExist(id).collect{ result ->
                _checkPasswordExistState.value = result
            }
        }
    }

    fun createNews(title: String, description: String, photos: List<File>){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}