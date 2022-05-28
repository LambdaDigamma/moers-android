package com.lambdadigamma.rubbish.source

import com.lambdadigamma.rubbish.RubbishCollectionStreet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RubbishRemoteDataSource(
    private val rubbishApi: RubbishApi,
    private val ioDispatcher: CoroutineDispatcher
) {

    /**
     * Fetches the streets registered in the rubbish collection system from
     * the network and returns the result.
     * This executes on an IO-optimized thread pool, the function is main-safe.
     */
    suspend fun fetchStreets(streetName: String? = null): List<RubbishCollectionStreet> {
        // Move the execution to an IO-optimized thread since the ApiService
        // doesn't support coroutines and makes synchronous requests.
        return withContext(ioDispatcher) {
            rubbishApi.fetchStreets(streetName = streetName).data
        }
    }

}