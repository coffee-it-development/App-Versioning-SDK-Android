package nl.coffeeit.appversioning.repository.network

import nl.coffeeit.appversioning.repository.network.interceptor.AddRequestHeadersInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit

class RestClient internal constructor(
    baseUrl: String,
    private val checkVersionEndpoint: String,
    private val versionName: String,
    private val enableLogging: Boolean
) {

    private val retrofitClient = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(buildHttpClient())
        .build()

    private val appVersionStatusService: AppVersionStatusService by lazy {
        retrofitClient.create(AppVersionStatusService::class.java)
    }

    fun getAppVersionStatus(): Response<Unit> {
        return appVersionStatusService.getAppVersionStatus(checkVersionEndpoint)
            .execute()
    }

    private fun buildHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (enableLogging) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        builder.addInterceptor(AddRequestHeadersInterceptor(versionName))

        return builder.build()
    }
}