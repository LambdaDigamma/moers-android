package com.lambdadigamma.fuel.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.gson.GsonBuilder
import com.lambdadigamma.core.LiveDataCallAdapterFactory
import com.lambdadigamma.core.Resource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FuelService {

    @GET("json/list.php")
    fun getPetrolStations(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("rad") radius: Double,
        @Query("sort") sorting: String,
        @Query("type") type: String
    ): LiveData<Resource<FuelRequestResponse>>

    @GET("json/detail.php")
    fun getPetrolStation(
        @Query("id") id: String
    ): LiveData<Resource<FuelStationDetailRequestResponse>>

    companion object Factory {

        private const val BASE_URL = "https://creativecommons.tankerkoenig.de/"

        fun getPetrolService(): FuelService {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain -> return@addInterceptor addApiKeyToRequests(chain) }
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
                    )
                )
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(FuelService::class.java)
        }

        private fun addApiKeyToRequests(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url()
            val newUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("apikey", "0dfdfad3-7385-ef47-2ff6-ec0477872677")
                .build()
            request.url(newUrl)
            Log.d("Api", newUrl.toString())
            return chain.proceed(request.build())
        }

    }

}