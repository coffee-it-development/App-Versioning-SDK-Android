package nl.coffeeit.appversioning.repository

import nl.coffeeit.appversioning.model.AppVersionStatus
import nl.coffeeit.appversioning.repository.network.ResponseHeaders
import nl.coffeeit.appversioning.repository.network.RestClient
import retrofit2.Response
import java.util.*

class AppVersionStatusRepository(
    private val restClient: RestClient
) {

    fun getAppVersionStatus(): Task<AppVersionStatus> {
        return Task {
            val response: Response<Unit> = restClient.getAppVersionStatus()

            val versionStatus: String? = response.headers()[ResponseHeaders.VERSION_STATUS]

            return@Task if (versionStatus == null) {
                AppVersionStatus.VALID
            } else {
                AppVersionStatus.valueOf(
                    versionStatus.toUpperCase(Locale.getDefault())
                )
            }
        }
    }
}