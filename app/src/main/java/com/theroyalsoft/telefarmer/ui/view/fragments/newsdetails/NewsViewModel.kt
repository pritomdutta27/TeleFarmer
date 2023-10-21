package com.theroyalsoft.telefarmer.ui.view.fragments.newsdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmer.primary.network.model.home.HomeResponse
import com.farmer.primary.network.repositorys.callhistory.CallHistoryRepository
import com.farmer.primary.network.repositorys.home.HomeRepository
import com.farmer.primary.network.repositorys.lapreport.LabReportRepository
import com.farmer.primary.network.utils.onError
import com.farmer.primary.network.utils.onException
import com.farmer.primary.network.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 22/10/23.
 */
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val pref: DataStoreRepository
) : ViewModel() {

    private val homeStateFlow by lazy {
        MutableSharedFlow<HomeResponse>()
    }
    val _homeStateFlow: SharedFlow<HomeResponse> = homeStateFlow

    private val errorFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val _errorFlow: SharedFlow<String> = errorFlow

    fun getHome() {
        viewModelScope.launch {
            homeRepository.fetchHome()
                .onSuccess { res ->
                    homeStateFlow.emit(res)
                }.onError { _, message ->
                    errorFlow.emit("Message: $message")
                }.onException { error ->
                    // Log.e("setMetaData", "setMetaData: "+error)
                    errorFlow.emit("$error")
                }
        }
    }
}