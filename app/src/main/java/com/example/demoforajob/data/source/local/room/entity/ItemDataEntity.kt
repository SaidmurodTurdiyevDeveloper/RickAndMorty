package com.example.demoforajob.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.demoforajob.data.model.ItemData

@Entity
data class ItemDataEntity(@PrimaryKey(autoGenerate = true) val defId: Long, var id: Int, var name: String, var loacation: String, var firstSeen: String, var imageUri: String, var status: String, var spacies: String)

fun ItemDataEntity.ChangeData():ItemData= ItemData(this.id,this.name,this.loacation,this.firstSeen,this.imageUri,"None",this.status,this.spacies)