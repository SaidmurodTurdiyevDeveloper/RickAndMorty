package com.example.demoforajob.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemDataEntity(@PrimaryKey(autoGenerate = true) val id: Long, var defId: Int, var name: String, var loacation: String, var firstSeen: String, var imageUri: String, var status: String, var spacies: String)
