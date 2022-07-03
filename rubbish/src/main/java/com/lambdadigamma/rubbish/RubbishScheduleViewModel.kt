package com.lambdadigamma.rubbish

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambdadigamma.core.Resource
import com.lambdadigamma.rubbish.source.DefaultRubbishApiService
import com.lambdadigamma.rubbish.source.RubbishRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RubbishScheduleViewModel @Inject constructor(
    private val rubbishDao: RubbishDao,
    @ApplicationContext val context: Context
) : ViewModel() {

    var rubbishSchedule: LiveData<Resource<List<RubbishCollectionItem>?>> =
        MutableLiveData(Resource.loading())

    private var rubbishRepository: RubbishRepository = RubbishRepository(
        remoteDataSource = RubbishRemoteDataSource(
            rubbishApi = DefaultRubbishApiService.getRubbishService(),
            ioDispatcher = Dispatchers.IO
        ),
        rubbishDao = rubbishDao,
        context = context
    )

    suspend fun load() {
        Log.d("UI", "Loading rubbish collection items")

        rubbishSchedule = rubbishRepository.loadRubbishCollectionItems()
//        news = newsRepository.getNews()
//
//        rubbishSchedule.rubbishSchedule = MutableLiveData(
//            Resource.success(rubbishRepository.loadRubbishCollectionItems())
//        )
    }

}
