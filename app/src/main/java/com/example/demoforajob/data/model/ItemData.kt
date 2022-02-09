package com.example.demoforajob.data.model

import com.example.demoforajob.data.source.local.room.entity.ItemDataEntity

data class ItemData(var id: Int, var name: String, var loacation: String, var firstSeen: String, var imageUri: String, var dataUri: String, var status: String, var spacies: String)

fun ItemData.ChangeEntity():ItemDataEntity=ItemDataEntity(0,this.id,this.name,this.loacation,this.firstSeen,this.imageUri,this.status,this.spacies)
