package xyz.looorielovbb.playground.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import xyz.looorielovbb.playground.pojo.Article
import xyz.looorielovbb.playground.pojo.BannerData
import xyz.looorielovbb.playground.pojo.Hotkey
import xyz.looorielovbb.playground.pojo.LR
import xyz.looorielovbb.playground.pojo.Resp

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
    ): Resp<LR<Article>>

    /**
     * 首页banner
     */
    @GET("banner/json")
    suspend fun getBanner(): Resp<List<BannerData>>

    /**
     * 搜索热词 https://www.wanandroid.com/hotkey/json
     * 数据类型
     */
    @GET("hotkey/json")
    suspend fun getHotKey(): Resp<List<Hotkey>>

}