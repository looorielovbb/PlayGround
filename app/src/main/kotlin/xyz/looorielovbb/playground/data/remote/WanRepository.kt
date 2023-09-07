package xyz.looorielovbb.playground.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import xyz.looorielovbb.playground.pojo.Article
import xyz.looorielovbb.playground.pojo.BannerData
import javax.inject.Inject

class WanRepository @Inject constructor(
    private val wanApiService: WanApiService
) {
    fun fetchArticles(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { ArticlesPagingSource(wanApiService) }
        ).flow
    }

    fun fetchBanner(): Flow<List<BannerData>> {
        return flow {
            wanApiService.getBanner()
            val list = wanApiService.getBanner().data
            emit(list)
        }.onStart {

        }.flowOn(Dispatchers.IO)
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}