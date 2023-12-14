package com.ajitesh.drape.data.repository

import com.ajitesh.drape.data.datasource.local.dao.ClothingDao
import com.ajitesh.drape.data.datasource.local.dao.LaundryDao
import com.ajitesh.drape.data.datasource.local.entity.Laundry
import com.ajitesh.drape.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val clothingDao: ClothingDao,
    private val laundryDao: LaundryDao
) : DetailRepository {
    override suspend fun get(id: Int) = clothingDao.get(id)

    override fun getLaundry(clothingId: Int) = laundryDao.getAllWhere(clothingId)

    override suspend fun addLaundry(id: Int) {
        val laundry = Laundry(clothingId = id, image = "")
        laundryDao.insertAll(*listOf(laundry).toTypedArray())
    }
}