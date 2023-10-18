package com.ajitesh.drape.data.datasource.local

import androidx.room.ColumnInfo

data class WornCount(
    val id: Int,
    val image: String,
    @ColumnInfo("worn_count") val wornCount: Int
)
