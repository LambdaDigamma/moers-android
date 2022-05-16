package com.lambdadigamma.rubbish.source

interface RubbishApi {

    suspend fun fetchStreets(): List<com.lambdadigamma.rubbish.RubbishCollectionStreet>

}