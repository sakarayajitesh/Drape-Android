package com.ajitesh.drape.data.repository

import com.ajitesh.drape.data.datasource.local.dao.ClothingDao
import com.ajitesh.drape.data.datasource.local.dao.LaundryDao
import com.ajitesh.drape.data.datasource.local.entity.Laundry
import com.ajitesh.drape.domain.repository.DetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val clothingDao: ClothingDao,
    private val laundryDao: LaundryDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DetailRepository {
    override suspend fun get(id: Int) = withContext(dispatcher) { clothingDao.get(id) }

    override fun getLaundry(clothingId: Int) = laundryDao.getAllWhere(clothingId)

    override suspend fun addLaundry(id: Int) {
        withContext(dispatcher) {
            val laundry = Laundry(clothingId = id, image = "")
            laundryDao.insertAll(*listOf(laundry).toTypedArray())
        }
    }

    override suspend fun deleteClothing(id: Int) = withContext(dispatcher) {
        clothingDao.delete(id)
    }
}