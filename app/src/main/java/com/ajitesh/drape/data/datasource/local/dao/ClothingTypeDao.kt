package com.ajitesh.drape.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ajitesh.drape.data.datasource.local.entity.ClothingType

@Dao
fun interface ClothingTypeDao {

    @Query("SELECT * FROM clothingtype")
    fun getAll(): List<ClothingType>
}