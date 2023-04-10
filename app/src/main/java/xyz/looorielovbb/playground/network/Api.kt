package xyz.looorielovbb.playground.network

import retrofit2.http.GET
import retrofit2.http.Path
import xyz.looorielovbb.playground.pojo.Article
import xyz.looorielovbb.playground.pojo.Base
import xyz.looorielovbb.playground.pojo.ListResp

interface Api {

    @GET("article/list/{page}/json")
    suspend fun getArticles(@Path("page")page:Int):Base<ListResp<Article>>

}