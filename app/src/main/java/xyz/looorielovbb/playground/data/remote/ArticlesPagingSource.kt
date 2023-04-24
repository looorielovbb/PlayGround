package xyz.looorielovbb.playground.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import xyz.looorielovbb.playground.pojo.Article

private const val UNSPLASH_STARTING_PAGE_INDEX = 0

class ArticlesPagingSource(
    private val wanApi: WanApi,
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = wanApi.getArticles(page, params.loadSize)
            val articles = response.data.datas
            LoadResult.Page(
                data = articles,
                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.data.total) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}