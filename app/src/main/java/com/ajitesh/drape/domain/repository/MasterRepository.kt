package com.ajitesh.drape.domain.repository

import com.ajitesh.drape.data.datasource.local.WornCount
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import kotlinx.coroutines.flow.Flow

interface MasterRepository {
    fun getFreshClothingList(): Flow<List<Clothing>>
    fun getHangerClothingList(): Flow<List<Clothing>>
    fun getBasketClothingList(): Flow<List<Clothing>>
}