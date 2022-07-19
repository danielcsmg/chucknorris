package br.com.zup.chucknorrisjokeapi.ui.cathegoryjoke.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zup.chucknorrisjokeapi.FAVORITE_MESSAGE
import br.com.zup.chucknorrisjokeapi.data.model.JokeCategory
import br.com.zup.chucknorrisjokeapi.data.model.JokeResponse
import br.com.zup.chucknorrisjokeapi.domain.repository.AuthenticationRepository
import br.com.zup.chucknorrisjokeapi.domain.repository.FavoriteJokeRepository
import br.com.zup.chucknorrisjokeapi.domain.usecase.CategoryJokeUseCase
import br.com.zup.desafiorickemorty.ui.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JokeViewModel : ViewModel() {
    private val authenticationRepository = AuthenticationRepository()
    private val favoriteJokeRepository = FavoriteJokeRepository()
    private val categoryJokeUseCase = CategoryJokeUseCase()

    private val jokeUseCase = CategoryJokeUseCase()

    private val _jokeResponse = MutableLiveData<ViewState<JokeResponse>>()
    val jokeResponse: LiveData<ViewState<JokeResponse>> = _jokeResponse

    private val _currentJoke = MutableLiveData<JokeResponse>()

    val jokeCategoryResponse = MutableLiveData<ViewState<JokeCategory>>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun getRandomJoke(category: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    categoryJokeUseCase.getRandomJoke(category)
                }
                _jokeResponse.value = response
            } catch (e: Exception) {
                ViewState.Error(Throwable("Erro"))
            }
        }
    }

    fun getCurrentJoke(joke: JokeResponse){
        _currentJoke.value = joke
    }

    fun getCategory() {
        try {
            viewModelScope.launch {
                val responseJoke = withContext(Dispatchers.IO) {
                    jokeUseCase.getCategory()
                }
                jokeCategoryResponse.value = responseJoke
            }
        } catch (e: Exception) {
            ViewState.Error(
                Throwable("A única categoria é Chuck Norris!")
            )
        }
    }

    fun saveFavoriteJoke() {
        val jokeId = _currentJoke.value?.id
        val joke = _currentJoke.value?.value

        jokeId?.let {
            favoriteJokeRepository.databaseReferences().child(jokeId)
                .setValue(
                    joke
                ) { error, _ ->
                    if(error != null){
                        _message.value = error.message
                    }
                    _message.value = FAVORITE_MESSAGE
                }
        }
    }

    fun getUserMail() = authenticationRepository.getUserEmail()

    fun logout() = authenticationRepository.logout()
}