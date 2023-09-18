package xyz.looorielovbb.playground.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import xyz.looorielovbb.playground.data.local.AppDatabase
import xyz.looorielovbb.playground.pojo.Article
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator constructor(
    private val wanApiService: WanApiService,
    private val db: AppDatabase,
) : RemoteMediator<Int, Article>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                // 首次访问 或者调用 PagingDataAdapter.refresh()
                LoadType.REFRESH -> null
                // 在当前加载的数据集的开头加载数据时
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                // 下来加载更多时触发
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    state.anchorPosition
                }
            }
            val response = wanApiService.getArticles(
                page = loadKey ?: 0, pageSize = state.config.pageSize
            )
            val dao = db.articleDao()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dao.clearAllArticles()
                }
                dao.insertAll(response.data.datas)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.data.curPage == response.data.pageCount
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    /* override suspend fun initialize(): InitializeAction {
         val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
         return if (System.currentTimeMillis() - db.lastUpdated() <= cacheTimeout) {
             InitializeAction.SKIP_INITIAL_REFRESH
         } else {
             InitializeAction.LAUNCH_INITIAL_REFRESH
         }
     }*/
}