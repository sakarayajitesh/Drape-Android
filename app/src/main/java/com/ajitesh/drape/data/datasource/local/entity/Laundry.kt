package com.ajitesh.drape.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Laundry(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "clothing_id") val clothingId: Int,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis()
){
    constructor(clothingId: Int, image: String) : this(
        id = 0,
        clothingId = clothingId,
        image = image
    )
}
