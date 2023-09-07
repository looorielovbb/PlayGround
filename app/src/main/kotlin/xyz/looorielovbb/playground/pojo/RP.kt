package xyz.looorielovbb.playground.pojo

/**
 * 后端返回基类
 */
data class RP<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String,
)