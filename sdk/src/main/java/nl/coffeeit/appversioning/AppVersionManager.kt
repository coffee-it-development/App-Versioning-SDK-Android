package nl.coffeeit.appversioning

import android.app.Activity
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import nl.coffeeit.appversioning.model.AppVersionStatus
import nl.coffeeit.appversioning.repository.network.RestClient
import nl.coffeeit.appversioning.repository.Task
import nl.coffeeit.appversioning.repository.AppVersionStatusRepository

class AppVersionManager private constructor(
    baseUrl: String,
    appVersion: String,
    config: Config
) {

    private val appVersionStatusRepository = AppVersionStatusRepository(
        RestClient(
            baseUrl,
            config.checkVersionEndpoint,
            appVersion,
            config.enableLogging
        )
    )

    fun getAppVersionStatus(): Task<AppVersionStatus> {
        return appVersionStatusRepository.getAppVersionStatus()
    }

    @JvmOverloads
    fun startUpdate(activity: Activity, requestCode: Int = REQUEST_CODE_APP_UPDATE) {
        val appUpdateManager = AppUpdateManagerFactory.create(activity)

        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {

                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    activity,
                    requestCode
                )
            }
        }
    }

    companion object {

        const val REQUEST_CODE_APP_UPDATE = 19000

        private var instance: AppVersionManager? = null

        @JvmOverloads
        fun init(
            baseUrl: String,
            appVersion: String,
            config: Config = Config()
        ): AppVersionManager {
            return AppVersionManager(
                baseUrl,
                appVersion,
                config
            ).also {
                instance = it
            }
        }

        fun getInstance(): AppVersionManager {
            return instance
                ?: throw RuntimeException("You need to initialize the AppVersionManager before you can use it.")
        }

        fun isInitialized(): Boolean = instance != null
    }

    class Config {

        internal var checkVersionEndpoint: String = "device/check-version"

        internal var enableLogging: Boolean = false

        fun setCheckVersionEndpoint(endpoint: String): Config = apply {
            checkVersionEndpoint = endpoint
        }

        fun setEnableLogging(enable: Boolean): Config = apply {
            enableLogging = enable
        }
    }
}