package ch.scout24.casestudy

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RestApi {

    @GET("/repos?per_page=20&page=1")
    suspend fun getRepos(): Response<List<RepoDataModelItem>>

    companion object {
        operator fun invoke(): RestApi {

            return Retrofit.Builder()
                .baseUrl("https://api.github.com/users/google")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RestApi::class.java)

        }
    }

}