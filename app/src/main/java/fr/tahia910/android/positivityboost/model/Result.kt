package fr.tahia910.android.positivityboost.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

data class Result<T>(
    val status: Status,
    val data: T?
) {
    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data)
        }
        fun <T> error(data: T? = null): Result<T> {
            return Result(Status.ERROR, data)
        }
        fun <T> loading(data: T? = null): Result<T> {
            return Result(Status.LOADING, data)
        }
    }
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map { Result.success(it) }
        .onStart { emit(Result.loading()) }
        .catch { emit(Result.error()) }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
