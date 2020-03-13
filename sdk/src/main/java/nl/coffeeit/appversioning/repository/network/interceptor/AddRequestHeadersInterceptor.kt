package nl.coffeeit.appversioning.repository.network.interceptor

import nl.coffeeit.appversioning.repository.network.RequestHeaders
import okhttp3.Interceptor
import okhttp3.Response

class AddRequestHeadersInterceptor(
    private val versionName: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(RequestHeaders.PLATFORM, "android")
            .addHeader(RequestHeaders.VERSION, versionName)
            .build()

        return chain.proceed(request)
    }
}