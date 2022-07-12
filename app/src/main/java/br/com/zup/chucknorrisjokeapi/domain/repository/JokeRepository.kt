package br.com.zup.chucknorrisjokeapi.domain.repository

import br.com.zup.chucknorrisjokeapi.data.datasource.remote.RetrofitService
import br.com.zup.chucknorrisjokeapi.data.model.JokeCategory
import br.com.zup.chucknorrisjokeapi.data.model.JokeResponse

class JokeRepository {

    suspend fun getRandomCNJoke(category: String): JokeResponse{
        return RetrofitService.apiService.getRamdomJoke(category)
    }

    suspend fun getRandomCNJoke(): JokeResponse{
        return RetrofitService.apiService.getRamdomJoke()
    }

    suspend fun getCategory(): JokeCategory {
        return RetrofitService.apiService.getCategory()
    }
}