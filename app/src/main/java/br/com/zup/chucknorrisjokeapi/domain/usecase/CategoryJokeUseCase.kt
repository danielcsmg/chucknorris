package br.com.zup.chucknorrisjokeapi.domain.usecase

import br.com.zup.chucknorrisjokeapi.RANDOM
import br.com.zup.chucknorrisjokeapi.data.model.JokeCategory
import br.com.zup.chucknorrisjokeapi.data.model.JokeResponse
import br.com.zup.chucknorrisjokeapi.domain.repository.JokeRepository
import br.com.zup.desafiorickemorty.ui.viewstate.ViewState

class CategoryJokeUseCase {
    private val jokeRepository = JokeRepository()

    suspend fun getRandomJoke(category: String): ViewState<JokeResponse> {
        return try {
            val randomJoke = if (category == RANDOM) {
                jokeRepository.getRandomCNJoke()
            } else {
                jokeRepository.getRandomCNJoke(category)
            }
            ViewState.Success(randomJoke)
        } catch (e: Exception) {
            ViewState.Error(Throwable("A sua internet fugiu de medo de Chuck Norris! Você também deveria!"))
        }
    }

    suspend fun getCategory(): ViewState<JokeCategory> {
        return try {
            val category = jokeRepository.getCategory()
            ViewState.Success(category)
        } catch (e: Exception) {
            ViewState.Error(Throwable("A única categoria é Chuck Norris"))
        }
    }
}