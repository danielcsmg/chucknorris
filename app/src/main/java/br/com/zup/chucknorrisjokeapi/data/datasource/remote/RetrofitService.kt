package br.com.zup.chucknorrisjokeapi.data.datasource.remote

import br.com.zup.chucknorrisjokeapi.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService {
    companion object {
        private const val BASE_URL = "https://api.chucknorris.io/"

        private val retrofit: Retrofit by lazy {

            //Configuração dos parametros de conexão
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(30, TimeUnit.SECONDS)
            httpClient.connectTimeout(30, TimeUnit.SECONDS)
            httpClient.writeTimeout(30, TimeUnit.SECONDS)

            //Se estivermos em modo DEBUG habilitamos os logs
            if (BuildConfig.DEBUG) {
                val logInterceptor = HttpLoggingInterceptor()
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(logInterceptor)
            }

            //Cria o builder do Retrofit
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        }
        @JvmStatic
        val apiService: JokeAPI
            get() = retrofit.create(JokeAPI::class.java)
    }
}