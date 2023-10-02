package com.farmer.primary.network.retrofitAdapter

import com.farmer.primary.network.utils.NetworkResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * Created by Pritom Dutta on 24/11/22.
 */
class NetworkResultCallAdapter(private val resultType: Type) :
    CallAdapter<Type, Call<NetworkResult<Type>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>): Call<NetworkResult<Type>> {
        return NetworkResultCall(call)
    }

}