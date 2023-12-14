package com.ajitesh.drape.domain.repository

import com.ajitesh.drape.data.datasource.local.entity.Laundry

interface LaundryRepository {
    suspend fun insertAll(laundryList: List<Laundry>)
    suspend fun get(id: Int): Laundry
}