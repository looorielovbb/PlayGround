package xyz.looorielovbb.playground.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.looorielovbb.playground.pojo.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}