package com.ajitesh.drape.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ajitesh.drape.data.datasource.local.dao.ClothingDao
import com.ajitesh.drape.data.datasource.local.dao.ClothingTypeDao
import com.ajitesh.drape.data.datasource.local.dao.LaundryDao
import com.ajitesh.drape.data.datasource.local.dao.MasterDao
import com.ajitesh.drape.data.datasource.local.dao.OutfitDao
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.ajitesh.drape.data.datasource.local.entity.ClothingType
import com.ajitesh.drape.data.datasource.local.entity.Laundry
import com.ajitesh.drape.data.datasource.local.entity.Outfit

@Database(
    entities = [Clothing::class, Outfit::class, Laundry::class, ClothingType::class],
    version = 7
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clothingDao(): ClothingDao
    abstract fun outfitDao(): OutfitDao
    abstract fun laundryDao(): LaundryDao
    abstract fun masterDao(): MasterDao
    abstract fun clothingTypeDao(): ClothingTypeDao
}