package com.ajitesh.drape.domain.repository

import com.ajitesh.drape.data.datasource.local.entity.Laundry
import kotlinx.coroutines.flow.Flow

interface LaundryRepository {
    fun getAll(): Flow<List<Laundry>>
    suspend fun insertAll(laundryList: List<Laundry>)
    suspend fun get(id: Int): Laundry
}