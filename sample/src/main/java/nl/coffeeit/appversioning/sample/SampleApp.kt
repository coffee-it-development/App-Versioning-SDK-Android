package nl.coffeeit.appversioning.sample

import android.app.Application
import nl.coffeeit.appversioning.BuildConfig
import nl.coffeeit.appversioning.AppVersionManager

@Suppress("unused")
class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val url = "https://dev.api.inlite.coffeeit.nl/"

        // see [AppVersionManagerE2ETest] for invalid/deprecated/valid version names
        val deprecatedVersionName = "1.0.1.3"

        val config = AppVersionManager.Config()
            .setEnableLogging(BuildConfig.DEBUG)

        AppVersionManager.init(
            baseUrl = url,
            appVersion = deprecatedVersionName,
            config = config
        )
    }
}