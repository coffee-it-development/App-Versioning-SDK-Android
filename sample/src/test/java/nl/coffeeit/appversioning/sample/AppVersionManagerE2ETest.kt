package nl.coffeeit.appversioning.sample

import nl.coffeeit.appversioning.AppVersionManager
import nl.coffeeit.appversioning.model.AppVersionStatus
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * End-to-End tests written for the [AppVersionManager]. These tests are expected to fail when the API of in-lite is down or changes.
 */
class AppVersionManagerE2ETest {

    private val url: String = "https://dev.api.inlite.coffeeit.nl/"

    @Test
    fun `test app version is invalid`() {
        val invalidVersionName = "1.0.1.2"
        val appVersionStatus = AppVersionManager.init(url, invalidVersionName).getAppVersionStatus().executeAwait()
        assertEquals(AppVersionStatus.INVALID, appVersionStatus)
    }

    @Test
    fun `test app version is deprecated`() {
        val deprecatedVersionName = "1.0.1.3"
        val appVersionStatus = AppVersionManager.init(url, deprecatedVersionName).getAppVersionStatus().executeAwait()
        assertEquals(AppVersionStatus.DEPRECATED, appVersionStatus)
    }

    @Test
    fun `test app version is valid`() {
        val validVersionName = "1.0.1.4"
        val appVersionStatus = AppVersionManager.init(url, validVersionName).getAppVersionStatus().executeAwait()
        assertEquals(AppVersionStatus.VALID, appVersionStatus)
    }
}