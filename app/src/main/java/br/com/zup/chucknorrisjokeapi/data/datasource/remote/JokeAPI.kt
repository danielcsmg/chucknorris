package br.com.zup.chucknorrisjokeapi.data.datasource.remote

import br.com.zup.chucknorrisjokeapi.data.model.JokeCategory
import br.com.zup.chucknorrisjokeapi.data.model.JokeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeAPI {
    @GET("jokes/random")
    suspend fun getRamdomJoke(
        @Query("category")
        category: String

    ): JokeResponse

    @GET("jokes/random")
    suspend fun getRamdomJoke(): JokeResponse

    @GET("jokes/categories")
    suspend fun getCategory(): JokeCategory
}
