package xyz.looorielovbb.playground.data.remote

import retrofit2.Retrofit
import xyz.looorielovbb.playground.network.Api
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val retrofit: Retrofit
) {
    suspend fun fetchArticles(){
        val apiService = retrofit.create(Api::class.java)
    }

}