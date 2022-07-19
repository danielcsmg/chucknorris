package br.com.zup.chucknorrisjokeapi.ui.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.chucknorrisjokeapi.domain.model.User
import br.com.zup.chucknorrisjokeapi.domain.repository.AuthenticationRepository

class RegisterViewModel: ViewModel() {
    private val authenticationRepository = AuthenticationRepository()

    private var _registerState = MutableLiveData<User>()
    val registerState: LiveData<User> = _registerState

    private var _errorState = MutableLiveData<String>()
    var errorState: LiveData<String> = _errorState

    fun validateDataUser(user: User){
        when{
            user.email.isEmpty() -> _errorState.value = "Insira um e-mail"
            user.password.isEmpty() -> _errorState.value = "Insira uma senha"
            else -> register(user)
        }
    }

    private fun register(user: User) {
        try {
            authenticationRepository.registerUser(
                user.email,
                user.password
            ).addOnSuccessListener {
//                authenticationRepository.updateUserProfile()
                _registerState.value = user
            }.addOnFailureListener {
                _errorState.value = "Usuário não foi registrado!" + it.message
            }
        }catch (e: Exception){
            _errorState.value = e.message
        }
    }
}