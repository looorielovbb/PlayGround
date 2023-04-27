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
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator @Inject constructor(
    private val db: AppDatabase,
    private val wanApi: WanApi
) : RemoteMediator<Int, Article>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    lastItem.id
                }
            }
            val response = wanApi.getArticles(
                page = 0, pageSize = 10
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
}