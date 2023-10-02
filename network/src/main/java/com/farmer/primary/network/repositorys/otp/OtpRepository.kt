package com.farmer.primary.network.repositorys.otp

import com.farmer.primary.network.model.otp.OtpParams
import com.farmer.primary.network.model.otp.OtpResponse
import com.farmer.primary.network.utils.NetworkResult

/**
 * Created by Pritom Dutta on 28/8/23.
 */
interface OtpRepository {
    suspend fun verifyOtp(params: OtpParams): NetworkResult<OtpResponse>
}