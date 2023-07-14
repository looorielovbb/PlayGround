package xyz.looorielovbb.playground.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.looorielovbb.playground.pojo.Article
import xyz.looorielovbb.playground.pojo.Base
import xyz.looorielovbb.playground.pojo.ListData

interface WanApiService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com/"
    }

    @GET("article/list/{page}/json")
    suspend fun getArticles(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Base<ListData<Article>>

}