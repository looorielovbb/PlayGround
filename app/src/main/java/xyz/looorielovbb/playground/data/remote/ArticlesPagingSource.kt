package xyz.looorielovbb.playground.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import xyz.looorielovbb.playground.pojo.Article

private const val STARTING_PAGE_INDEX = 0

class ArticlesPagingSource(
    private val wanApi: WanApi,
) : PagingSource<Int, Article>() {

    companion object{
        const val TAG= "ArticlesPagingSource"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            Log.d(TAG, "load ArticlesPagingSource")
            val response = wanApi.getArticles(page, params.loadSize)
            val articles = response.data.datas
            LoadResult.Page(
                data = articles,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.data.total) null else page + 1
            )
        } catch (exception: Exception) {
            Log.e(TAG, exception.message?:"")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        Log.d(TAG, "getRefreshKey: $state")
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}