package com.lambdadigamma.radio

import androidx.lifecycle.LiveData
import com.google.gson.GsonBuilder
import com.lambdadigamma.core.ApplicationServerService
import com.lambdadigamma.core.DataResponse
import com.lambdadigamma.core.LiveDataCallAdapterFactory
import com.lambdadigamma.core.Resource
import com.lambdadigamma.core.utils.AcceptLanguageHeaderInterceptor
import com.lambdadigamma.radio.models.RadioBroadcast
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RadioBroadcastService : ApplicationServerService {

    @GET("radio-broadcasts")
    fun getUpcomingBroadcasts(): LiveData<Resource<DataResponse<List<RadioBroadcast>>>>

    companion object Factory {

        fun getService(): RadioBroadcastService {

            val client = OkHttpClient()
                .newBuilder()
                .addInterceptor(AcceptLanguageHeaderInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl("https://moers.app/api/v1/")
                .client(client)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
                    )
                )
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(RadioBroadcastService::class.java)
        }

    }

}