package com.example.demoforajob.data.source.local.room.dao

import androidx.room.*
import com.example.demoforajob.data.source.local.room.entity.ItemDataEntity

@Dao
interface MainDao {
    @Query("Select * From itemdataentity")
    suspend fun getAllItems(): List<ItemDataEntity>

    @Delete
    suspend fun deleteList(list: List<ItemDataEntity>):Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addlist(list: List<ItemDataEntity>)
}