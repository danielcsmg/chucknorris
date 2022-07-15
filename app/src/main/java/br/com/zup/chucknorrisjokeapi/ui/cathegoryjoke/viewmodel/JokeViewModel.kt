package br.com.zup.chucknorrisjokeapi.ui.cathegoryjoke.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zup.chucknorrisjokeapi.data.model.JokeCategory
import br.com.zup.chucknorrisjokeapi.data.model.JokeResponse
import br.com.zup.chucknorrisjokeapi.domain.repository.AuthenticationRepository
import br.com.zup.chucknorrisjokeapi.domain.usecase.CategoryJokeUseCase
import br.com.zup.desafiorickemorty.ui.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JokeViewModel : ViewModel() {
    private val authenticationRepository = AuthenticationRepository()
    private val jokeUseCase = CategoryJokeUseCase()
    private val _jokeResponse = MutableLiveData<ViewState<JokeResponse>>()
    val jokeResponse: LiveData<ViewState<JokeResponse>> = _jokeResponse
    val jokeCathegoryResponse = MutableLiveData<ViewState<JokeCategory>>()

    fun getRandomJoke(cathegory: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    jokeUseCase.getRandomJoke(cathegory)
                }
                _jokeResponse.value = response
            } catch (e: Exception) {
                _jokeResponse.value = ViewState.Error(
                    Throwable("A sua internet fugiu de medo de Chuck Norris! Você também deveria!")
                )
            }
        }
    }

    fun getCategory() {
        try {
            viewModelScope.launch {
                val responseJoke = withContext(Dispatchers.IO) {
                    jokeUseCase.getCathegory()
                }
                jokeCathegoryResponse.value = responseJoke
            }
        } catch (e: Exception) {
            ViewState.Error(
                Throwable("A única categoria é Chuck Norris!")
            )
        }
    }

    fun logout() = authenticationRepository.logout()
}