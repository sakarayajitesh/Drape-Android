package com.ajitesh.drape.data.di

import android.app.Application
import androidx.room.Room
import com.ajitesh.drape.data.datasource.local.AppDatabase
import com.ajitesh.drape.data.datasource.local.dao.ClothingDao
import com.ajitesh.drape.data.datasource.local.dao.LaundryDao
import com.ajitesh.drape.data.datasource.local.dao.MasterDao
import com.ajitesh.drape.data.datasource.local.dao.OutfitDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            "Drape"
        ).createFromAsset("predrape.db")
            .build()


    @Provides
    @Singleton
    fun provideClothingDao(appDatabase: AppDatabase): ClothingDao {
        return appDatabase.clothingDao()
    }

    @Provides
    @Singleton
    fun provideOutfitDao(appDatabase: AppDatabase): OutfitDao {
        return appDatabase.outfitDao()
    }

    @Provides
    @Singleton
    fun provideLaundryDao(appDatabase: AppDatabase): LaundryDao {
        return appDatabase.laundryDao()
    }

    @Provides
    @Singleton
    fun provideMasterDao(appDatabase: AppDatabase): MasterDao {
        return appDatabase.masterDao()
    }

}