package com.lambdadigamma.rubbish.source

import com.lambdadigamma.core.DataResponse
import com.lambdadigamma.rubbish.RubbishCollectionStreet
import com.lambdadigamma.rubbish.RubbishPickupItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DefaultRubbishApiService : RubbishApi {

    @GET("rubbish/streets?all")
    override suspend fun fetchStreets(@Query("q") streetName: String?): DataResponse<List<RubbishCollectionStreet>>

    @GET("rubbish/streets/{id}/pickups")
    suspend fun getPickupItems(@Path("id") streetId: Int): List<RubbishPickupItem>

    companion object Factory {

        fun getRubbishService(): DefaultRubbishApiService {
            return Retrofit.Builder()
                .baseUrl("https://moers.app/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(DefaultRubbishApiService::class.java)
        }

    }

}