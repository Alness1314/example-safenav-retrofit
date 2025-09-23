package com.alness.myapplication.api

import com.alness.myapplication.helpers.TypeHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

class GenericRepository @Inject constructor(
    private val api: GenericApi,
    private val gson: Gson
) {

    suspend fun <T> get(
        url: String,
        headers: Map<String, String> = emptyMap(),
        queries: Map<String, String> = emptyMap(),
        clazz: Class<T>? = null,       // para objetos simples
        listType: Boolean = false      // si esperamos lista
    ): Any? {
        val resp = api.get(url, headers, queries)
        return handleResponse(resp, clazz, listType)
    }

    suspend fun <T> post(
        url: String,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        clazz: Class<T>? = null,
        listType: Boolean = false
    ): Any? {
        val resp = api.post(url, headers, body)
        return handleResponse(resp, clazz, listType)
    }

    suspend fun <T> put(
        url: String,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        clazz: Class<T>? = null,
        listType: Boolean = false
    ): Any? {
        val resp = api.put(url, headers, body)
        return handleResponse(resp, clazz, listType)
    }

    suspend fun delete(
        url: String,
        headers: Map<String, String> = emptyMap()
    ): Boolean {
        val resp = api.delete(url, headers)
        return resp.isSuccessful
    }

    suspend fun <T> postMultipart(
        url: String,
        parts: List<MultipartBody.Part>,
        headers: Map<String, String> = emptyMap(),
        clazz: Class<T>? = null,
        listType: Boolean = false
    ): Any? {
        val resp = api.postMultipart(url, headers, parts)
        return handleResponse(resp, clazz, listType)
    }


    //suspend fun get(url: String, headers: Map<String, String> = emptyMap(), queries: Map<String, String> = emptyMap()): Response<ResponseBody> =
        //withContext(Dispatchers.IO) { api.get(url, headers, queries) }


    /*suspend fun postJson(url: String, bodyObj: Any, headers: Map<String, String> = emptyMap()): Response<ResponseBody> =
        withContext(Dispatchers.IO) {
            val json = gson.toJson(bodyObj)
            val rb = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            api.postJson(url, headers, rb)
        }*/


    /*suspend fun putJson(url: String, bodyObj: Any, headers: Map<String, String> = emptyMap()): Response<ResponseBody> =
        withContext(Dispatchers.IO) {
            val json = gson.toJson(bodyObj)
            val rb = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            api.putJson(url, headers, rb)
        }*/


    /*suspend fun delete(url: String, bodyObj: Any? = null, headers: Map<String, String> = emptyMap()): Response<ResponseBody> =
        withContext(Dispatchers.IO) {
            val rb = bodyObj?.let { gson.toJson(it).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()) }
            api.deleteWithBody(url, headers, rb)
        }*/


    // Helper: crear MultipartBody.Part a partir de File
    /*private fun fileToPart(partName: String, file: File, contentType: String? = null): MultipartBody.Part {
        val mt = (contentType ?: "application/octet-stream").toMediaTypeOrNull()
        val reqBody = file.asRequestBody(mt)
        return MultipartBody.Part.createFormData(partName, file.name, reqBody)
    }*/


    /*suspend fun postFile(
        url: String,
        fileParamName: String,
        file: File,
        otherParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): Response<ResponseBody> = withContext(Dispatchers.IO) {
        val part = fileToPart(fileParamName, file)
        val map = otherParams.mapValues { it.value.toRequestBody("text/plain".toMediaTypeOrNull()) }
        api.postMultipart(url, headers, listOf(part), map)
    }*/


    /*suspend fun putFile(
        url: String,
        fileParamName: String,
        file: File,
        otherParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): Response<ResponseBody> = withContext(Dispatchers.IO) {
        val part = fileToPart(fileParamName, file)
        val map = otherParams.mapValues { it.value.toRequestBody("text/plain".toMediaTypeOrNull()) }
        api.putMultipart(url, headers, listOf(part), map)
    }*/


    private fun <T> handleResponse(
        resp: retrofit2.Response<okhttp3.ResponseBody>,
        clazz: Class<T>?,
        listType: Boolean
    ): Any? {
        if (resp.isSuccessful) {
            val bodyStr = resp.body()?.string() ?: return null
            return when {
                clazz != null -> TypeHelper.fromJson(bodyStr, clazz)
                listType -> throw IllegalArgumentException("List type requires clazz parameter")
                else -> bodyStr
            }
        } else {
            throw Exception("Error ${resp.code()}: ${resp.errorBody()?.string()}")
        }
    }

    // Método para listas
    suspend fun <T> getList(
        url: String,
        headers: Map<String, String> = emptyMap(),
        queries: Map<String, String> = emptyMap(),
        clazz: Class<T>
    ): List<T> {
        val resp = api.get(url, headers, queries)
        if (resp.isSuccessful) {
            val bodyStr = resp.body()?.string() ?: return emptyList()
            return TypeHelper.listFromJson(bodyStr, clazz)
        } else {
            throw Exception("Error ${resp.code()}: ${resp.errorBody()?.string()}")
        }
    }
}