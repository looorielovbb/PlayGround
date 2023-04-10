package xyz.looorielovbb.playground.pojo

import com.google.gson.annotations.SerializedName


data class Article(

    @SerializedName("adminAdd") var adminAdd: Boolean? = null,
    @SerializedName("apkLink") var apkLink: String? = null,
    @SerializedName("audit") var audit: Int? = null,
    @SerializedName("author") var author: String? = null,
    @SerializedName("canEdit") var canEdit: Boolean? = null,
    @SerializedName("chapterId") var chapterId: Int? = null,
    @SerializedName("chapterName") var chapterName: String? = null,
    @SerializedName("collect") var collect: Boolean? = null,
    @SerializedName("courseId") var courseId: Int? = null,
    @SerializedName("desc") var desc: String? = null,
    @SerializedName("descMd") var descMd: String? = null,
    @SerializedName("envelopePic") var envelopePic: String? = null,
    @SerializedName("fresh") var fresh: Boolean? = null,
    @SerializedName("host") var host: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("isAdminAdd") var isAdminAdd: Boolean? = null,
    @SerializedName("link") var link: String? = null,
    @SerializedName("niceDate") var niceDate: String? = null,
    @SerializedName("niceShareDate") var niceShareDate: String? = null,
    @SerializedName("origin") var origin: String? = null,
    @SerializedName("prefix") var prefix: String? = null,
    @SerializedName("projectLink") var projectLink: String? = null,
    @SerializedName("publishTime") var publishTime: Int? = null,
    @SerializedName("realSuperChapterId") var realSuperChapterId: Int? = null,
    @SerializedName("route") var route: Boolean? = null,
    @SerializedName("selfVisible") var selfVisible: Int? = null,
    @SerializedName("shareDate") var shareDate: Int? = null,
    @SerializedName("shareUser") var shareUser: String? = null,
    @SerializedName("superChapterId") var superChapterId: Int? = null,
    @SerializedName("superChapterName") var superChapterName: String? = null,
    @SerializedName("tags") var tags: ArrayList<String> = arrayListOf(),
    @SerializedName("title") var title: String? = null,
    @SerializedName("type") var type: Int? = null,
    @SerializedName("userId") var userId: Int? = null,
    @SerializedName("visible") var visible: Int? = null,
    @SerializedName("zan") var zan: Int? = null

)