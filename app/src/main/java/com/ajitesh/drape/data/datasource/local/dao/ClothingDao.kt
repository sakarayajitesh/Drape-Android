package com.ajitesh.drape.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothingDao {

    @Query("SELECT * FROM clothing")
    fun getAll(): Flow<List<Clothing>>

    @Query("SELECT * FROM clothing WHERE id=:id")
    suspend fun get(id: Int): Clothing

    @Query("SELECT * FROM clothing WHERE id IN (:ids)")
    suspend fun getByIds(ids: List<Int>): List<Clothing>

    @Insert
    suspend fun insertAll(vararg clothings: Clothing)

    @Query("DELETE FROM clothing WHERE id=:id")
    suspend fun delete(id: Int): Int

}