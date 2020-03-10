package nl.coffeeit.appversioning.repository

import android.os.Handler
import android.os.Looper

class Task<T>(
    private val action: () -> T
) {

    private var taskResult: TaskResult<T>? = null

    private var thread: Thread? = null

    fun executeAsync(
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ): Task<T> {
        synchronized(this) {
            thread = Thread(Runnable {
                try {
                    val actionResult = action.invoke()
                    taskResult =
                        TaskResult.Success(
                            actionResult
                        )
                    Handler(Looper.getMainLooper()).post {
                        onSuccess(actionResult)
                    }
                } catch (e: Throwable) {
                    taskResult =
                        TaskResult.Error(
                            e
                        )
                    Handler(Looper.getMainLooper()).post {
                        onError(e)
                    }
                }
            }).also {
                it.start()
            }
        }

        return this
    }

    @Throws(Exception::class)
    fun executeAwait(): T {
        return action.invoke()
    }

    @Throws(Exception::class)
    fun getResult(): T {
        val myResult = taskResult ?: throw NullPointerException()

        when (myResult) {
            is TaskResult.Success -> {
                return myResult.data
            }
            is TaskResult.Error -> {
                throw myResult.error
            }
        }
    }

    fun cancel() {
        synchronized(this) {
            try {
                thread?.interrupt()
            } catch (e: Exception) {

            }
        }
    }
}