package com.lambdadigamma.rubbish

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lambdadigamma.core.Resource
import com.lambdadigamma.rubbish.settings.RubbishSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class RubbishScheduleViewModel @Inject constructor(
    private val rubbishRepository: RubbishRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

    var rubbishSchedule: MutableLiveData<Resource<List<RubbishCollectionItem>?>> =
        MutableLiveData(Resource.loading())

    var rubbishCollectionStreet: LiveData<Resource<RubbishSettings.RubbishCollectionStreet>> =
        MutableLiveData(Resource.loading())

//    private var rubbishRepository: RubbishRepository = RubbishRepository(
//        remoteDataSource = RubbishRemoteDataSource(
//            rubbishApi = DefaultRubbishApiService.getRubbishService(),
//            ioDispatcher = Dispatchers.IO
//        ),
//        remote
//        rubbishDao = rubbishDao,
//        context = context
//    )

    suspend fun load() {
        Log.d("UI", "Loading rubbish collection items")

        rubbishCollectionStreet = rubbishRepository
            .currentStreet
            .map { Resource.success(it) }
            .asLiveData()

        rubbishRepository.remoteDataSource.getPickupItems(4)
            .observeForever {
                Log.d("UI", "Received rubbish collection items")
                Log.d("UI", it.toString())

                rubbishSchedule.postValue(Resource.success(it.data?.data.orEmpty()))
            }

//        rubbishSchedule =
//            Transformations.map(rubbishRepository.remoteDataSource.getPickupItems(1)) {
//                Log.d("UI", "Received rubbish collection items")
//                return@map Resource.success(it.data?.data.orEmpty())
//            }

//        rubbishSchedule = rubbishRepository.loadRubbishCollectionItems()
//        news = newsRepository.getNews()
//
//        rubbishSchedule.rubbishSchedule = MutableLiveData(
//            Resource.success(rubbishRepository.loadRubbishCollectionItems())
//        )
    }

}
