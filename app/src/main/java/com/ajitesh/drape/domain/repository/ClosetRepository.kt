package com.ajitesh.drape.domain.repository

import com.ajitesh.drape.data.datasource.local.entity.Clothing
import kotlinx.coroutines.flow.Flow

interface ClosetRepository {
    fun getAll(): Flow<List<Clothing>>
    suspend fun insertAll(clothingList: List<Clothing>)
    suspend fun get(id: Int): Clothing
}