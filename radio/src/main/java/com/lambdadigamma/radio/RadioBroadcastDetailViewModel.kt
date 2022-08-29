package com.lambdadigamma.radio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lambdadigamma.core.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RadioBroadcastDetailViewModel @Inject constructor(
    private val radioBroadcastService: RadioBroadcastService
) : ViewModel() {

    var radioBroadcastId: Int? = null

    fun load(): LiveData<Resource<BroadcastDetailUiState>> {

        return if (radioBroadcastId != null) {
            return Transformations.map(radioBroadcastService.show(radioBroadcastId!!)) { resource ->
                return@map resource.transform { dataResponse ->
                    BroadcastDetailUiState(
                        title = dataResponse.data.title,
                        description = dataResponse.data.description,
                        startsAt = dataResponse.data.startsAt,
                        endsAt = dataResponse.data.endsAt,
                        imageUrl = dataResponse.data.attach
                    )
                }
            }
        } else {
            MutableLiveData(Resource.loading())
        }

    }


}