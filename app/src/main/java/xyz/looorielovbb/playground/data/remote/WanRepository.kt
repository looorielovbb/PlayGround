package xyz.looorielovbb.playground.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import xyz.looorielovbb.playground.pojo.Article
import javax.inject.Inject

class WanRepository @Inject constructor(
    private val wanApi: WanApi
) {
    fun fetchArticles(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { ArticlesPagingSource(wanApi) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}