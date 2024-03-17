package ru.hmhub.agents.data.in_memory

import androidx.compose.runtime.collectAsState
import ru.hmhub.agents.data.remote.RemoteRepository
import ru.hmhub.agents.data.remote.serializables.EmployeeSerializable
import ru.hmhub.agents.ui.view_models.RemoteViewModel
import ru.hmhub.agents.utils.SharedPreferencesManager

class InMemoryHelper(
    private val sharedPreferencesManager: SharedPreferencesManager
) {
    val users = mutableListOf<EmployeeSerializable>()
    fun saveUserId(id: Int){
        deleteUserId()
        sharedPreferencesManager.setInt("user_id", id)
    }

    fun deleteUserId(){
        try {
            sharedPreferencesManager.deleteInt("user_id")
        } catch (e: Exception){
            println(e.stackTraceToString())
        }
    }

    fun getUserId(): Int{
        return sharedPreferencesManager.getInt("user_id", 999)
    }

    fun saveUser(user: EmployeeSerializable){
        users.clear()
        users.add(user)
    }

    fun getCurrentUser(): EmployeeSerializable?{
        return users.firstOrNull()
    }
}