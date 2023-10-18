package com.ajitesh.drape.data.di

import com.ajitesh.drape.data.datasource.local.dao.ClothingDao
import com.ajitesh.drape.data.datasource.local.dao.LaundryDao
import com.ajitesh.drape.data.datasource.local.dao.MasterDao
import com.ajitesh.drape.data.datasource.local.dao.OutfitDao
import com.ajitesh.drape.domain.repository.ClosetRepository
import com.ajitesh.drape.data.repository.ClosetRepositoryImpl
import com.ajitesh.drape.data.repository.LaundryRepositoryImpl
import com.ajitesh.drape.data.repository.MasterRepositoryImpl
import com.ajitesh.drape.data.repository.OutfitRepositoryImpl
import com.ajitesh.drape.domain.repository.LaundryRepository
import com.ajitesh.drape.domain.repository.MasterRepository
import com.ajitesh.drape.domain.repository.OutfitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideClothingRepository(clothingDao: ClothingDao): ClosetRepository {
        return ClosetRepositoryImpl(clothingDao)
    }

    @Provides
    fun provideOutfitRepository(outfitDao: OutfitDao): OutfitRepository {
        return OutfitRepositoryImpl(outfitDao)
    }

    @Provides
    fun provideMasterRepository(masterDao: MasterDao, clothingDao: ClothingDao): MasterRepository {
        return MasterRepositoryImpl(masterDao, clothingDao)
    }

    @Provides
    fun provideLaundryRepository(laundryDao: LaundryDao): LaundryRepository {
        return LaundryRepositoryImpl(laundryDao)
    }

}