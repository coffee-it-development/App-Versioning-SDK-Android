package nl.coffeeit.appversioning.repository

sealed class TaskResult<out T> {

    class Success<T>(val data: T) : TaskResult<T>()

    class Error(val error: Throwable) : TaskResult<Nothing>()
}