package com.lambdadigamma.moers.data.rubbish.source

import com.lambdadigamma.moers.data.rubbish.RubbishCollectionStreet

interface RubbishApi {

    suspend fun fetchStreets(): List<RubbishCollectionStreet>

}