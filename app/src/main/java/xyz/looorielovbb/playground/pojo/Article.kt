package xyz.looorielovbb.playground.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey
    @SerializedName("id") @ColumnInfo(name = "id") val id: Int? = 0,
    @SerializedName("adminAdd") val adminAdd: Boolean? =false,
    @SerializedName("apkLink") val apkLink: String?= "",
    @SerializedName("audit") val audit: Int? =0,
    @SerializedName("author") val author: String?= "",
    @SerializedName("canEdit") val canEdit: Boolean?=false,
    @SerializedName("chapterId") val chapterId: Int?= 0,
    @SerializedName("chapterName") val chapterName: String?= "",
    @SerializedName("collect") val collect: Boolean?=false,
    @SerializedName("courseId") val courseId: Int?= 0,
    @SerializedName("desc") val desc: String?= "",
    @SerializedName("descMd") val descMd: String?= "",
    @SerializedName("envelopePic") val envelopePic: String?= "",
    @SerializedName("fresh") val fresh: Boolean?=false,
    @SerializedName("host") val host: String?= "",
    @SerializedName("isAdminAdd") val isAdminAdd: Boolean?=false,
    @SerializedName("link") val link: String?= "",
    @SerializedName("niceDate") val niceDate: String?= "",
    @SerializedName("niceShareDate") val niceShareDate: String?= "",
    @SerializedName("origin") val origin: String?= "",
    @SerializedName("prefix") val prefix: String?= "",
    @SerializedName("projectLink") val projectLink: String?= "",
    @SerializedName("publishTime") val publishTime: Int?= 0,
    @SerializedName("realSuperChapterId")  val realSuperChapterId: Int?= 0,
    @SerializedName("route") val route: Boolean?=false,
    @SerializedName("selfVisible") val selfVisible: Int?= 0,
    @SerializedName("shareDate") val shareDate: Int?= 0,
    @SerializedName("shareUser") val shareUser: String?= "",
    @SerializedName("superChapterId") val superChapterId: Int?= 0,
    @SerializedName("superChapterName") val superChapterName: String?= "",
    @SerializedName("tags") @Ignore val tags: ArrayList<String> = arrayListOf(),
    @SerializedName("title") val title: String?= "",
    @SerializedName("type") val type: Int?= 0,
    @SerializedName("userId") val userId: Int?= 0,
    @SerializedName("visible") val visible: Int?= 0,
    @SerializedName("zan") val zan: Int?= 0

)