package com.lambdadigamma.rubbish.source

import com.lambdadigamma.core.DataResponse
import com.lambdadigamma.rubbish.RubbishCollectionItem
import com.lambdadigamma.rubbish.RubbishCollectionStreet

interface RubbishApi {

    suspend fun fetchStreets(streetName: String? = null): DataResponse<List<RubbishCollectionStreet>>

    suspend fun getPickupItems(streetId: Long): DataResponse<List<RubbishCollectionItem>>

}