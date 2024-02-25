package com.ajitesh.drape.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Clothing::class,
        childColumns = ["clothing_id"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Outfit(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "clothing_id") val clothingId: Int,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis()
) {
    constructor(clothingId: Int, image: String) : this(
        id = 0,
        clothingId = clothingId,
        image = image
    )
}
