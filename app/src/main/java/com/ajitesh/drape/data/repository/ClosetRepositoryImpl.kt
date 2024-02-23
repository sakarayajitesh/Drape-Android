package com.ajitesh.drape.data.repository

import com.ajitesh.drape.data.datasource.local.dao.ClothingDao
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.ajitesh.drape.domain.repository.ClosetRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClosetRepositoryImpl @Inject constructor(
    private val dao: ClothingDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ClosetRepository {

    init {
        println("ClosetRepositoryImpl - init")
    }

    override fun getAll(): Flow<List<Clothing>> {
        val result = dao.getAll()
        println("ClosetRepositoryImpl - Got data")
        return result
    }

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