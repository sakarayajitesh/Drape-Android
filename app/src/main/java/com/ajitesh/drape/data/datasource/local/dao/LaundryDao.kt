package com.ajitesh.drape.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ajitesh.drape.data.datasource.local.entity.Laundry
import kotlinx.coroutines.flow.Flow

@Dao
interface LaundryDao {

    @Query("SELECT * FROM laundry")
    fun getAll(): Flow<List<Laundry>>

    @Query("SELECT * FROM laundry WHERE id=:id")
    suspend fun get(id: Int): Laundry

    @Insert
    suspend fun insertAll(vararg laundriesEntities: Laundry)

}