package com.theroyalsoft.telefarmer.utils

import bio.medico.patient.model.apiResponse.ResponseMetaInfo
import com.squareup.moshi.Moshi
import java.io.IOException

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
}