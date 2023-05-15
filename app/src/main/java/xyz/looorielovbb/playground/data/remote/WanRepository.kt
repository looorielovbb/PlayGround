package xyz.looorielovbb.playground.data.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import xyz.looorielovbb.playground.pojo.Article
import javax.inject.Inject

class WanRepository @Inject constructor(
    private val wanApiService: WanApiService
) {
    fun fetchArticles(): Flow<PagingData<Article>> {
        Log.d(TAG, "fetchArticles: ")
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { ArticlesPagingSource(wanApiService) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
        const val TAG ="WanRepository"
    }
}