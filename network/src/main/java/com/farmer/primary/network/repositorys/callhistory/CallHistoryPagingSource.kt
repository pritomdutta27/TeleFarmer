package com.farmer.primary.network.repositorys.callhistory

import androidx.paging.PagingSource
import androidx.paging.PagingState
import bio.medico.patient.model.apiResponse.ResponseCallHistory
import bio.medico.patient.model.apiResponse.ResponseCallHistoryModel
import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.getBearerToken
import com.farmer.primary.network.utils.onSuccess
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import java.lang.Exception

/**
 * Created by Pritom Dutta on 21/10/23.
 */
class CallHistoryPagingSource(
    private val api: FarmerApi,
    private val pref: DataStoreRepository,
    private val userInfo: String,
    private val uuid: String
) : PagingSource<Int, ResponseCallHistory>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseCallHistory> {
        return try {
            val position = params.key ?: 1
            val token = pref.getString(AppConstants.PREF_KEY_ACCESS_TOKEN) ?: ""
            var mlist : List<ResponseCallHistory> = emptyList()
            api.invoke(token.getBearerToken(), userInfo, uuid, "20", position.toString()).onSuccess {
                mlist = it.callHistory
            }

            return LoadResult.Page(
                data = mlist,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == 100) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResponseCallHistory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}