package xyz.looorielovbb.playground.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.looorielovbb.playground.data.remote.ArticlesPagingSource
import xyz.looorielovbb.playground.data.remote.WanApiService
import xyz.looorielovbb.playground.pojo.Article
import xyz.looorielovbb.playground.pojo.BannerData
import javax.inject.Inject

class WanRepository @Inject constructor(
    private val api: WanApiService
) {
    private val pagingConfig = PagingConfig(
        // 每页显示的数据的大小
        pageSize = 10,
        // 开启占位符
        enablePlaceholders = false,
        // 预刷新的距离，距离最后一个 item 多远时加载数据
        // 默认为 pageSize
        prefetchDistance = 2,
        /**
         * 初始化加载数量，默认为 pageSize * 3
         *
         * internal const val DEFAULT_INITIAL_PAGE_MULTIPLIER = 3
         * val initialLoadSize: Int = pageSize * DEFAULT_INITIAL_PAGE_MULTIPLIER
         */
        initialLoadSize = 10,
    )


    fun fetchArticles(): Flow<PagingData<Article>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { ArticlesPagingSource(api) },
            initialKey = 0,
        ).flow

    }

    fun fetchBanner(): Flow<List<BannerData>> {
        return flow {
            api.getBanner()
            val list = api.getBanner().data
            emit(list)
        }
    }
}