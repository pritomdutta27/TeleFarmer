package com.farmer.primary.network.repositorys.otp

import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.model.otp.OtpParams
import com.farmer.primary.network.model.otp.OtpResponse
import com.farmer.primary.network.utils.NetworkResult
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 28/8/23.
 */

class OtpRepositoryImp @Inject constructor(private val api: FarmerApi) : OtpRepository {
    override suspend fun verifyOtp(params: OtpParams): NetworkResult<OtpResponse> {
        return api.invoke(params)
    }
}