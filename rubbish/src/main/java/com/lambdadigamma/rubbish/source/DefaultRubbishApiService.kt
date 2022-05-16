package com.lambdadigamma.rubbish.source

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DefaultRubbishApiService : RubbishApi {

    @GET("rubbish/streets?all")
    override suspend fun fetchStreets(): List<com.lambdadigamma.rubbish.RubbishCollectionStreet>

    @GET("rubbish/streets/{id}/pickups")
    suspend fun getPickupItems(@Path("id") streetId: Int): List<com.lambdadigamma.rubbish.RubbishPickupItem>

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