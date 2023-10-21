package com.farmer.primary.network.repositorys.callhistory

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import bio.medico.patient.model.apiResponse.ResponseCallHistory
import bio.medico.patient.model.apiResponse.ResponseCallHistoryModel
import bio.medico.patient.model.apiResponse.ResponseSingleDoctor
import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.NetworkResult
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 21/10/23.
 */
class CallHistoryRepositoryImp @Inject constructor(
    private val api: FarmerApi,
    private val pref: DataStoreRepository
) : CallHistoryRepository {
    //    override fun getCallHistory(
//        userInfo: String,
//        uuid: String
//    ): Flow<PagingData<ResponseCallHistory>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 10,
//                maxSize = 100,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = {
//                CallHistoryPagingSource(
//                    api = api,
//                    pref,
//                    userInfo = userInfo,
//                    uuid = uuid
//                )
//            }
//        ).flow
//    }
    override suspend fun getCallHistory(
        userInfo: String,
        uuid: String
    ): NetworkResult<ResponseCallHistoryModel> {
        return api.invoke(
            pref.getString(AppConstants.PREF_KEY_ACCESS_TOKEN) ?: "",
            userInfo,
            uuid,
            "1",
            "20"
        )
    }


}