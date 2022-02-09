package com.example.demoforajob.data.repository

import com.example.demoforajob.data.model.ChangeEntity
import com.example.demoforajob.data.model.ItemData
import com.example.demoforajob.data.source.local.room.dao.MainDao
import com.example.demoforajob.data.source.local.room.entity.ItemDataEntity
import com.example.demoforajob.data.source.remote.RemoteApi
import com.example.demoforajob.data.source.remote.model.ResponceData
import com.example.demoforajob.domen.repository.RepositoryMain
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RepositoryMainImplament constructor(private var api: RemoteApi, private var databse: MainDao) : RepositoryMain {

    override suspend fun getItemsToRemote(): Response<ResponceData> = api.getCurrentData()

    override suspend fun getItemsToStoradge(): List<ItemDataEntity> = databse.getAllItems()

    override suspend fun saveItemsToStoradge(list: List<ItemData>) {
        databse.addlist(list.map {
            it.ChangeEntity()
        })
    }

    override suspend fun deleteItemsToStoradge(list: List<ItemDataEntity>) {
        databse.deleteList(list)
    }
}