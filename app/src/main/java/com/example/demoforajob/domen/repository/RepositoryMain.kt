package com.example.demoforajob.domen.repository

import com.example.demoforajob.data.model.ItemData
import com.example.demoforajob.data.source.local.room.entity.ItemDataEntity
import com.example.demoforajob.data.source.remote.model.ResponceData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepositoryMain {
    suspend fun getItemsToRemote(): Response<ResponceData>
    suspend fun getItemsToStoradge(): List<ItemDataEntity>
    suspend fun saveItemsToStoradge(list: List<ItemData>)
    suspend fun deleteItemsToStoradge(list: List<ItemDataEntity>)
}