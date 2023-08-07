package xyz.looorielovbb.playground.data.remote

sealed class ApiState {
    data object Loading : ApiState()
    class Failure(val e: Throwable) : ApiState()
    class Success(val data: Any) : ApiState()
    data object Empty : ApiState()
}
