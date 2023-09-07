package xyz.looorielovbb.playground.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import xyz.looorielovbb.playground.pojo.Article
import java.io.IOException

private const val STARTING_PAGE_INDEX = 0

class ArticlesPagingSource(
    private val wanApiService: WanApiService,
) : PagingSource<Int, Article>() {

    companion object{
        const val TAG= "ArticlesPagingSource"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = wanApiService.getArticles(nextPageNumber, params.loadSize)
            val articles = response.data.datas
            LoadResult.Page(
                data = articles,
                prevKey = if (nextPageNumber == STARTING_PAGE_INDEX) null else nextPageNumber - 1,
                nextKey = if (nextPageNumber == response.data.total) null else nextPageNumber + 1
            )
        } catch (e: IOException) {
            // IOException for network failures.
            Log.e(TAG, "load: ",e)
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            Log.e(TAG, "load: ",e)
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