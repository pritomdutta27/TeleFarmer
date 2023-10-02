package com.theroyalsoft.telefarmer.ui.view.fragments.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmer.primary.network.model.metadata.MetaDataResponse
import com.farmer.primary.network.model.metadata.MetaModel
import com.farmer.primary.network.repositorys.metadata.MetaDataRepository
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.onError
import com.farmer.primary.network.utils.onException
import com.farmer.primary.network.utils.onSuccess
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 6/9/23.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MetaDataRepository,
    private val pref: DataStoreRepository
): ViewModel() {


}