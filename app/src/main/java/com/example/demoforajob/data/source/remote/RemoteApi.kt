package com.example.demoforajob.data.source.remote

import com.example.demoforajob.data.source.remote.model.ResponceData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface RemoteApi {
    @GET("character")
    suspend fun getCurrentData(): Response<ResponceData>
}