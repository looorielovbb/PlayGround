package xyz.looorielovbb.playground.pojo

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article  @JvmOverloads constructor(
    @PrimaryKey
    val id: Int,
    val adminAdd: Boolean,
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val host: String,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Int,
    val realSuperChapterId: Int,
    val route: Boolean,
    val selfVisible: Int,
    val shareDate: Int,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int,
    @Ignore val tags: List<Tag> = listOf()
)
data class Tag(
    val name: String,
    val url: String
)