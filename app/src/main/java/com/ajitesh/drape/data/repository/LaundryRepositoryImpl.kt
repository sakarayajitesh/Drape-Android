package com.ajitesh.drape.data.repository

import com.ajitesh.drape.data.datasource.local.dao.LaundryDao
import com.ajitesh.drape.data.datasource.local.entity.Laundry
import com.ajitesh.drape.domain.repository.LaundryRepository
import javax.inject.Inject

class LaundryRepositoryImpl @Inject constructor(private val laundryDao: LaundryDao) :
    LaundryRepository {

    override fun getAll() = laundryDao.getAll()

    override suspend fun insertAll(laundryList: List<Laundry>) {
       laundryDao.insertAll(*laundryList.toTypedArray())
    }

    override suspend fun get(id: Int) = laundryDao.get(id)
}