package com.ajitesh.drape.data.repository

import com.ajitesh.drape.data.datasource.local.dao.OutfitDao
import com.ajitesh.drape.data.datasource.local.entity.Outfit
import com.ajitesh.drape.domain.repository.OutfitRepository
import com.ajitesh.drape.groupByTimestamp
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class OutfitRepositoryImpl @Inject constructor(
    private val outfitDao: OutfitDao
) : OutfitRepository {

    private val currentDate = LocalDate.now()

    override fun getAllAsMap() = outfitDao.getAll().map {
        it.reversed().groupByTimestamp()
    }.map {
        it.mapKeys { (localDate, _) ->
            when(localDate){
                currentDate -> "Today"
                currentDate.minusDays(1) -> "Yesterday"
                else -> localDate.toString()
            }
        }
    }

    override suspend fun insertAll(outfitList: List<Outfit>) {
        outfitDao.insertAll(*outfitList.toTypedArray())
    }

    override suspend fun get(id: Int) = outfitDao.get(id)
}