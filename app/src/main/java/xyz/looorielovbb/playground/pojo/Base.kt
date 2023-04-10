package xyz.looorielovbb.playground.pojo

data class Base<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String,
)