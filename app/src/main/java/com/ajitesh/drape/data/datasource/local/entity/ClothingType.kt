package com.ajitesh.drape.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClothingType(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String
)
