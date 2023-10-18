package com.ajitesh.drape.data.repository

import com.ajitesh.drape.data.datasource.local.dao.ClothingDao
import com.ajitesh.drape.data.datasource.local.dao.MasterDao
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.ajitesh.drape.domain.repository.MasterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MasterRepositoryImpl(
    private val masterDao: MasterDao,
    private val clothingDao: ClothingDao
) : MasterRepository {


    override fun getFreshClothingList(): Flow<List<Clothing>> {
        return masterDao.get()
            .map { wornCountList ->
                wornCountList.filter { it.wornCount == 0 }
            }
            .map { filteredWornCountList ->
                val clothingListIds = filteredWornCountList.map { it.id }
                clothingDao.getByIds(clothingListIds)
            }
    }

    override fun getHangerClothingList(): Flow<List<Clothing>> {
        return masterDao.get().map {
            val clothingList = mutableListOf<Clothing>()
            it.map { wornCount->
                val clothing = clothingDao.get(wornCount.id)
                if(0 < wornCount.wornCount && wornCount.wornCount < clothing.wearLimit){
                    clothingList.add(clothing)
                }
            }
            clothingList.toList()
        }
    }


    override fun getBasketClothingList(): Flow<List<Clothing>> {
        return masterDao.get().map {
            val clothingList = mutableListOf<Clothing>()
            it.map { wornCount->
                val clothing = clothingDao.get(wornCount.id)
                if(wornCount.wornCount >= clothing.wearLimit){
                    clothingList.add(clothing)
                }
            }
            clothingList.toList()
        }
    }
}