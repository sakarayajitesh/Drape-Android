package com.ajitesh.drape.data.repository

import com.ajitesh.drape.data.datasource.local.dao.ClothingDao
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.ajitesh.drape.domain.repository.ClosetRepository
import javax.inject.Inject

class ClosetRepositoryImpl @Inject constructor(private val dao: ClothingDao): ClosetRepository {

    override fun getAll() = dao.getAll()

    override suspend fun insertAll(clothingList: List<Clothing>){
        dao.insertAll(*clothingList.toTypedArray())
    }

    override suspend fun get(id: Int) = dao.get(id)
}