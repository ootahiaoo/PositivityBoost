package fr.tahia910.android.positivityboost.model

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

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}