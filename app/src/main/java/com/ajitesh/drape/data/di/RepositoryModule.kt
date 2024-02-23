package com.ajitesh.drape.data.di

import com.ajitesh.drape.data.datasource.local.dao.ClothingDao
import com.ajitesh.drape.data.datasource.local.dao.LaundryDao
import com.ajitesh.drape.data.datasource.local.dao.MasterDao
import com.ajitesh.drape.data.datasource.local.dao.OutfitDao
import com.ajitesh.drape.data.repository.ClosetRepositoryImpl
import com.ajitesh.drape.data.repository.DetailRepositoryImpl
import com.ajitesh.drape.data.repository.LaundryRepositoryImpl
import com.ajitesh.drape.data.repository.MasterRepositoryImpl
import com.ajitesh.drape.data.repository.OutfitRepositoryImpl
import com.ajitesh.drape.domain.repository.ClosetRepository
import com.ajitesh.drape.domain.repository.DetailRepository
import com.ajitesh.drape.domain.repository.LaundryRepository
import com.ajitesh.drape.domain.repository.MasterRepository
import com.ajitesh.drape.domain.repository.OutfitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideClothingRepository(clothingDao: ClothingDao): ClosetRepository {
        return ClosetRepositoryImpl(clothingDao)
    }

    @Provides
    @Singleton
    fun provideOutfitRepository(outfitDao: OutfitDao): OutfitRepository {
        return OutfitRepositoryImpl(outfitDao)
    }

    @Provides
    @Singleton
    fun provideMasterRepository(masterDao: MasterDao, clothingDao: ClothingDao): MasterRepository {
        return MasterRepositoryImpl(masterDao, clothingDao)
    }

    @Provides
    @Singleton
    fun provideLaundryRepository(laundryDao: LaundryDao): LaundryRepository {
        return LaundryRepositoryImpl(laundryDao)
    }

    @Provides
    @Singleton
    fun provideDetailRepository(
        clothingDao: ClothingDao,
        laundryDao: LaundryDao
    ): DetailRepository {
        return DetailRepositoryImpl(clothingDao, laundryDao)
    }

}