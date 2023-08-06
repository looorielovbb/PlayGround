package xyz.looorielovbb.playground.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.looorielovbb.playground.pojo.Article
import xyz.looorielovbb.playground.pojo.BannerData
import xyz.looorielovbb.playground.pojo.LR
import xyz.looorielovbb.playground.pojo.RP

interface WanApiService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com/"
    }

    /**
     * 首页
     */
    @GET("article/list/{page}/json")
    suspend fun getArticles(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int
    ): RP<LR<Article>>

    /**
     * 首页banner
     */
    @GET("banner/json")
    suspend fun getBanner(): RP<List<BannerData>>

}