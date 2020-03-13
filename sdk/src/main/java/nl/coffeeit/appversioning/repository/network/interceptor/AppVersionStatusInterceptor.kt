package nl.coffeeit.appversioning.repository.network.interceptor

import nl.coffeeit.appversioning.model.AppVersionStatus
import nl.coffeeit.appversioning.repository.network.ResponseHeaders
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

class AppVersionStatusInterceptor(
    private val listener: Listener
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)
        response.header(ResponseHeaders.VERSION_STATUS)?.let { versionStatus ->
            listener.appVersionStatusIntercepted(
                AppVersionStatus.valueOf(
                    versionStatus.toUpperCase(
                        Locale.getDefault()
                    )
                )
            )
        }

        return response
    }

    interface Listener {

        @Throws(Exception::class)
        fun appVersionStatusIntercepted(appVersionStatus: AppVersionStatus)
    }
}