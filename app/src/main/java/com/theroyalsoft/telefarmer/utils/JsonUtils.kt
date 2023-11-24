package com.theroyalsoft.telefarmer.utils

import bio.medico.patient.model.apiResponse.ResponseMetaInfo
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.theroyalsoft.telefarmer.model.loan.LoanDetailsResponseItem
import java.io.IOException
import java.lang.reflect.Type

/**
 * Created by Pritom Dutta on 15/10/23.
 */
object JsonUtils {

    @Throws(IOException::class)
    private fun readFileToString(contextClass: Class<*>, fileName: String): String {
        contextClass.getResourceAsStream(fileName)
            ?.bufferedReader().use {
                val jsonString = it?.readText() ?: ""
                it?.close()
                return jsonString
            }
    }

    fun getMetaData(fileName: String): ResponseMetaInfo {
        val moshi = Moshi.Builder()
            .build()
        val jsonAdapter = moshi.adapter(ResponseMetaInfo::class.java)
        val jsonString = readFileToString(JsonUtils::class.java, "/$fileName")
        return jsonAdapter.fromJson(jsonString)!!
    }

    fun getProfile(fileName: String): bio.medico.patient.model.apiResponse.ResponsePatientInfo {
        val moshi = Moshi.Builder()
            .build()
        val jsonAdapter = moshi.adapter(bio.medico.patient.model.apiResponse.ResponsePatientInfo::class.java)
        val jsonString = readFileToString(JsonUtils::class.java, "/$fileName")
        return jsonAdapter.fromJson(jsonString)!!
    }

    fun getCropDetails(fileName: String): List<LoanDetailsResponseItem> {
        val moshi = Moshi.Builder()
            .build()
        val listData: Type = Types.newParameterizedType(
            List::class.java,
            LoanDetailsResponseItem::class.java
        )
        val adapter: JsonAdapter<List<LoanDetailsResponseItem>> = moshi.adapter(listData)
        val jsonString = readFileToString(JsonUtils::class.java, "/$fileName")
        return adapter.fromJson(jsonString)!!
    }
}