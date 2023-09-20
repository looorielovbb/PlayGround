package xyz.looorielovbb.playground.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import xyz.looorielovbb.playground.pojo.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Article>)

    @Query("SELECT * FROM articles WHERE id = :id")
    fun articlePagingSource(id: Int): PagingSource<Int, Article>

    @Query("SELECT * FROM articles WHERE id = :id")
    fun getArticleById(id: String): Flow<Article>

    @Query("DELETE FROM articles")
    suspend fun clearAllArticles()

    @Query("SELECT * FROM articles ORDER BY id DESC")
    fun getCachedArticles(): PagingSource<Int, Article>
}