package nl.coffeeit.appversioning.sdk.repository

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import nl.coffeeit.appversioning.model.AppVersionStatus
import nl.coffeeit.appversioning.repository.AppVersionStatusRepository
import nl.coffeeit.appversioning.repository.network.ResponseHeaders
import nl.coffeeit.appversioning.repository.network.RestClient
import okhttp3.Headers
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class AppVersionStatusRepositoryTest {

    @Test
    fun `test app version is invalid`() {
        val response = Response.success(Unit, Headers.headersOf(
            ResponseHeaders.VERSION_STATUS, "invalid"
        ))

        val mockRestClient = mock<RestClient> {
            on { getAppVersionStatus() } doReturn response
        }

        val appVersionStatusRepository = AppVersionStatusRepository(mockRestClient)

        val versionStatus = appVersionStatusRepository.getAppVersionStatus().executeAwait()
        assertEquals(AppVersionStatus.INVALID, versionStatus)

        verify(mockRestClient, times(1)).getAppVersionStatus()
    }

    @Test
    fun `test app version is deprecated`() {
        val response = Response.success(Unit, Headers.headersOf(
            ResponseHeaders.VERSION_STATUS, "deprecated"
        ))

        val mockRestClient = mock<RestClient> {
            on { getAppVersionStatus() } doReturn response
        }

        val appVersionStatusRepository = AppVersionStatusRepository(mockRestClient)

        val versionStatus = appVersionStatusRepository.getAppVersionStatus().executeAwait()
        assertEquals(AppVersionStatus.DEPRECATED, versionStatus)

        verify(mockRestClient, times(1)).getAppVersionStatus()
    }

    @Test
    fun `test app version is valid`() {
        val response = Response.success(Unit, Headers.headersOf(
            ResponseHeaders.VERSION_STATUS, "valid"
        ))

        val mockRestClient = mock<RestClient> {
            on { getAppVersionStatus() } doReturn response
        }

        val appVersionStatusRepository = AppVersionStatusRepository(mockRestClient)

        val versionStatus = appVersionStatusRepository.getAppVersionStatus().executeAwait()
        assertEquals(AppVersionStatus.VALID, versionStatus)

        verify(mockRestClient, times(1)).getAppVersionStatus()
    }
}