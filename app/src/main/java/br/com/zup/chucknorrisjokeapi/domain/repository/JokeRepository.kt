package br.com.zup.chucknorrisjokeapi.domain.repository

import br.com.zup.chucknorrisjokeapi.data.datasource.remote.RetrofitService
import br.com.zup.chucknorrisjokeapi.data.model.JokeCategory
import br.com.zup.chucknorrisjokeapi.data.model.JokeResponse

class JokeRepository {
    suspend fun getRandomCNJoke(cathegory: String): JokeResponse{
        return RetrofitService.apiService.getRamdomJoke(cathegory)
    }

    suspend fun getRandomCNJoke(): JokeResponse{
        return RetrofitService.apiService.getRamdomJoke()
    }

    suspend fun getCathegory(): JokeCategory {
        return RetrofitService.apiService.getCategory()
    }
}