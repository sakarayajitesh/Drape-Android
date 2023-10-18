package com.ajitesh.drape.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ajitesh.drape.data.datasource.local.entity.Outfit
import kotlinx.coroutines.flow.Flow

@Dao
interface OutfitDao{

    @Query("SELECT * FROM outfit")
    fun getAll(): Flow<List<Outfit>>

    @Query("SELECT * FROM outfit WHERE id=:id")
    suspend fun get(id: Int): Outfit

    @Insert
    suspend fun insertAll(vararg outfitEntities: Outfit)
}