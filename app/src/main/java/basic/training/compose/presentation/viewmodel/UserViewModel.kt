package basic.training.compose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import basic.training.compose.domain.model.User
import basic.training.compose.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val getUserUseCase: GetUsersUseCase) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: MutableStateFlow<List<User>> = _users

    private val _isLoading = MutableStateFlow(false)
    val isLoading : MutableStateFlow<Boolean> = _isLoading

    fun searchUsers(query : String, page : Int = 1, perPage : Int = 10) {
        viewModelScope.launch {
            _isLoading.value = true
            _users.value = getUserUseCase.execute(query, page,perPage)
            _isLoading.value = false
        }
    }
}