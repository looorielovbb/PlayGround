@file:Suppress("unused")
package xyz.looorielovbb.playground.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import xyz.looorielovbb.playground.pojo.Article
import java.io.IOException


class ArticlesPagingSource(
    private val wanApiService: WanApiService,
) : PagingSource<Int, Article>() {

    companion object {
        const val TAG = "ArticlesPagingSource"
        const val DEFAULT_PAGE_SIZE: Int = 10
        const val DEFAULT_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = wanApiService.getArticles(page)
            val articles = response.data.datas

            LoadResult.Page(
                data = articles,
                prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.data.pageCount - 1) null else page + 1
            )
        } catch (e: IOException) {
            Log.e(TAG, e.message, e.cause)
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.e(TAG, e.message(), e.fillInStackTrace())
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}