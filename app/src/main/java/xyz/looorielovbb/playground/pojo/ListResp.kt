package xyz.looorielovbb.playground.pojo

data class ListResp<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Long,
)
