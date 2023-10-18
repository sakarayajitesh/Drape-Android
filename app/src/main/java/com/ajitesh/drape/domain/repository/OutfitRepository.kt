package com.ajitesh.drape.domain.repository

import com.ajitesh.drape.data.datasource.local.entity.Outfit
import kotlinx.coroutines.flow.Flow

interface OutfitRepository {
    fun getAllAsMap(): Flow<Map<String, List<Outfit>>>
    suspend fun insertAll(outfitList: List<Outfit>)
    suspend fun get(id: Int): Outfit
}