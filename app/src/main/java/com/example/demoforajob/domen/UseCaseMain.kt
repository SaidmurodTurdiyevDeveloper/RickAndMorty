package com.example.demoforajob.domen

import com.example.demoforajob.data.model.ItemData
import com.example.demoforajob.zzz_others.MyResponce
import kotlinx.coroutines.flow.Flow

interface UseCaseMain {
    fun loadItems(): Flow<MyResponce<List<ItemData>>>
}