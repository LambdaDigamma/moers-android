package com.lambdadigamma.moers.data.rubbish.source

import com.lambdadigamma.moers.data.rubbish.RubbishCollectionStreet
import com.lambdadigamma.moers.data.rubbish.RubbishPickupItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DefaultRubbishApiService : RubbishApi {

    @GET("rubbish/streets?all")
    override suspend fun fetchStreets(): List<RubbishCollectionStreet>

    @GET("rubbish/streets/{id}/pickups")
    suspend fun getPickupItems(@Path("id") streetId: Int): List<RubbishPickupItem>

    companion object Factory {

        fun getRubbishService(): DefaultRubbishApiService {
            return Retrofit.Builder()
                .baseUrl("https://moers.app/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(DefaultRubbishApiService::class.java)
        }

    }

}