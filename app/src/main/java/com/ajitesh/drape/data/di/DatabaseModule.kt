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

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            "drape-database"
        )
            .createFromAsset("myapp.db")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    fun provideClothingDao(appDatabase: AppDatabase): ClothingDao {
        return appDatabase.clothingDao()
    }

    @Provides
    fun provideOutfitDao(appDatabase: AppDatabase): OutfitDao {
        return appDatabase.outfitDao()
    }

    @Provides
    fun provideLaundryDao(appDatabase: AppDatabase): LaundryDao {
        return appDatabase.laundryDao()
    }

    @Provides
    fun provideMasterDao(appDatabase: AppDatabase): MasterDao {
        return appDatabase.masterDao()
    }

}