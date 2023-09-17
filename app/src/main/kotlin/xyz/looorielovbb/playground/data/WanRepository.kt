package xyz.looorielovbb.playground.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.looorielovbb.playground.data.local.AppDatabase
import xyz.looorielovbb.playground.data.remote.ArticleRemoteMediator
import xyz.looorielovbb.playground.data.remote.ArticlesPagingSource
import xyz.looorielovbb.playground.data.remote.WanApiService
import xyz.looorielovbb.playground.pojo.Article
import xyz.looorielovbb.playground.pojo.BannerData
import javax.inject.Inject

class WanRepository @Inject constructor(
    private val api: WanApiService,
    private val db: AppDatabase
) {
    @OptIn(ExperimentalPagingApi::class)
    fun fetchArticles(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            initialKey = 0,
            remoteMediator = ArticleRemoteMediator(db, api),
            pagingSourceFactory = { ArticlesPagingSource(api) },
        ).flow

    }

    fun fetchBanner(): Flow<List<BannerData>> {
        return flow {
            api.getBanner()
            val list = api.getBanner().data
            emit(list)
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}