package com.ajitesh.drape.data.repository

import com.ajitesh.drape.data.datasource.local.WornCount
import com.ajitesh.drape.data.datasource.local.dao.ClothingDao
import com.ajitesh.drape.data.datasource.local.dao.MasterDao
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.ajitesh.drape.domain.repository.MasterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.mapNotNull

class MasterRepositoryImpl(
    private val masterDao: MasterDao,
    private val clothingDao: ClothingDao
) : MasterRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getClothingList(wornCountFilter: (WornCount) -> Boolean): Flow<List<Clothing>>{
        return masterDao.get()
            .mapNotNull { wornCountList ->
                wornCountList.filter(wornCountFilter)
            }
            .mapLatest { filteredWornCountList ->
                val clothingListIds = filteredWornCountList.map { it.id }
                clothingDao.getByIds(clothingListIds)
            }
    }

    override fun getFreshClothingList(): Flow<List<Clothing>> {
        return getClothingList {
            it.wornCount == 0
        }
    }

    override fun getHangerClothingList(): Flow<List<Clothing>> {
        return getClothingList {
            0 < it.wornCount && it.wornCount < it.wearLimit
        }
    }

    override fun getBasketClothingList(): Flow<List<Clothing>> {
        return getClothingList {
            it.wornCount >= it.wearLimit
        }
    }
}