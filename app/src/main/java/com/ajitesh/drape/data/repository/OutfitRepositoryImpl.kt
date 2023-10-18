package com.ajitesh.drape.data.repository

import com.ajitesh.drape.data.datasource.local.dao.OutfitDao
import com.ajitesh.drape.data.datasource.local.entity.Outfit
import com.ajitesh.drape.domain.repository.OutfitRepository
import javax.inject.Inject

class OutfitRepositoryImpl @Inject constructor(
    private val outfitDao: OutfitDao
) : OutfitRepository {

    override fun getAll() = outfitDao.getAll()

    override suspend fun insertAll(outfitList: List<Outfit>) {
        outfitDao.insertAll(*outfitList.toTypedArray())
    }

    override suspend fun get(id: Int) = outfitDao.get(id)
}