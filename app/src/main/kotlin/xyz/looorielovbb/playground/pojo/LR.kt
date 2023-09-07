package xyz.looorielovbb.playground.pojo

/**
 * 列表基类
 */
data class LR<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
)
