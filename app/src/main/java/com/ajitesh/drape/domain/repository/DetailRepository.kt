package com.ajitesh.drape.domain.repository

import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.ajitesh.drape.data.datasource.local.entity.Laundry
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun get(id: Int): Clothing

    fun getLaundry(clothingId: Int): Flow<List<Laundry>>

    suspend fun addLaundry(id: Int)
    suspend fun deleteClothing(id: Int): Int
}