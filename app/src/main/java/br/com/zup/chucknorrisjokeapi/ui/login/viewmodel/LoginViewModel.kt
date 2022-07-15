package br.com.zup.chucknorrisjokeapi.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.chucknorrisjokeapi.domain.model.User
import br.com.zup.chucknorrisjokeapi.domain.repository.AuthenticationRepository

class LoginViewModel: ViewModel() {
    private val authenticationRepository = AuthenticationRepository()

    private var _loginState = MutableLiveData<User>()
    var loginState: LiveData<User> = _loginState

    private var _errorState = MutableLiveData<String>()
    var errorState: LiveData<String> = _errorState

    fun validate(user: User){
        when{
            user.email.isEmpty() -> _errorState.value = "Preencha o campo de usuário!"
            user.password.isEmpty() -> _errorState.value = "Preencha o campo de senha!"
            else -> login(user)
        }
    }

    private fun login(user: User) {
        try {
            authenticationRepository.loginUser(
                user.email,
                user.password
            ).addOnSuccessListener {
                _loginState.value = user
            }.addOnFailureListener {
                _errorState.value = "Erro ao tentar logar" + it.message
            }
        }catch (e: Exception) {
            _errorState.value = e.message
        }
    }
}