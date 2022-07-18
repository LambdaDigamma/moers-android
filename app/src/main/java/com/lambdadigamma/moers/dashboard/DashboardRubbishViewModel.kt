package com.lambdadigamma.moers.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lambdadigamma.core.Resource
import com.lambdadigamma.rubbish.RubbishCollectionItem
import com.lambdadigamma.rubbish.RubbishRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class DashboardRubbishViewModel @Inject constructor(
    private val rubbishRepository: RubbishRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

//    var rubbishSchedule: Flow<Resource<List<RubbishCollectionItem>>>
//
//    var rubbishSchedule: MutableLiveData<Resource<List<RubbishCollectionItem>>> =
//        MutableLiveData(Resource.loading())

    fun load(): LiveData<Resource<List<List<RubbishCollectionItem>>>> {

        return Transformations.map(rubbishRepository.loadRubbishCollectionItemsFromNetwork()) { resource ->
            resource.transform { collectionItems ->
                collectionItems.groupBy { it.parsedDate }.values.take(3)
            }
        }

    }

}