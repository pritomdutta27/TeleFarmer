package com.farmer.primary.network.repositorys.lapreport

import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.utils.AppConstants
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 21/10/23.
 */
class LabReportRepositoryImp @Inject constructor(
    private val api: FarmerApi,
    private val pref: DataStoreRepository
) : LabReportRepository {
    override suspend fun getLapReports(
        userInfo: String,
        uuid: String
    ) = api.invoke(
        pref.getString(AppConstants.PREF_KEY_ACCESS_TOKEN) ?: "",
        userInfo,
        uuid
    )

    override suspend fun uploadImgFile(
        userInfo: String,
        uuid: String,
        imageBody: MultipartBody.Part,
        folder: String,
        channel: String,
        url: String
    ) = api.invoke(
        token = pref.getString(AppConstants.PREF_KEY_ACCESS_TOKEN) ?: "",
        userInfo = userInfo,
        uuid = uuid, imageBody = imageBody, folder = folder, channel = channel, url = url
    )
}