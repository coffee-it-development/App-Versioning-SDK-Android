package nl.coffeeit.appversioning.repository.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface AppVersionStatusService {

    @GET
    fun getAppVersionStatus(@Url endpoint: String): Call<Unit>
}