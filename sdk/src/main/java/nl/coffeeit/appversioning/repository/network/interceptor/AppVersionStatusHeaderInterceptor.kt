package nl.coffeeit.appversioning.repository.network.interceptor

import nl.coffeeit.appversioning.repository.network.RequestHeaders
import nl.coffeeit.appversioning.repository.network.ResponseHeaders
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AppVersionStatusHeaderInterceptor(
    private val versionName: String,
    private val statusInterceptedAction: (String) -> Unit = {}
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
                .newBuilder()
                .addHeader(RequestHeaders.PLATFORM, "android")
                .addHeader(RequestHeaders.VERSION, versionName)
                .build()

        val response = chain.proceed(request)
        response.header(ResponseHeaders.VERSION_STATUS)?.let { versionStatus ->
            statusInterceptedAction.invoke(versionStatus)
        }

        return response
    }
}