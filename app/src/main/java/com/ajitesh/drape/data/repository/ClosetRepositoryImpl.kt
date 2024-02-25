package com.ajitesh.drape.data.repository

import com.ajitesh.drape.data.datasource.local.dao.ClothingDao
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.ajitesh.drape.domain.repository.ClosetRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClosetRepositoryImpl @Inject constructor(
    private val dao: ClothingDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ClosetRepository {


    override fun getAll() = dao.getAll()

    override suspend fun insertAll(clothingList: List<Clothing>) {
        withContext(dispatcher) {
            dao.insertAll(*clothingList.toTypedArray())
        }
    }

    override suspend fun get(id: Int) = withContext(dispatcher) { dao.get(id) }

    override suspend fun deleteClothing(id: Int) = withContext(dispatcher) {
        dao.delete(id)
    }
}