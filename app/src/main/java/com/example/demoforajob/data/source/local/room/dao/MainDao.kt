package com.example.demoforajob.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.demoforajob.data.source.local.room.entity.ItemDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {
    @Query("Select * From itemdataentity")
    fun getAllItems(): Flow<List<ItemDataEntity>>

    @Delete
    suspend fun deleteList(list: List<ItemDataEntity>)

    @Insert
    suspend fun addlist(list: List<ItemDataEntity>)
}