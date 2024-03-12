package com.ajitesh.drape.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ajitesh.drape.data.datasource.local.WornCount
import kotlinx.coroutines.flow.Flow

@Dao
fun interface MasterDao {

    @Query(
        "SELECT *, count(clothing_id) as worn_count from Clothing\n " +
                "LEFT JOIN (SELECT o.clothing_id from Outfit as o\n " +
                "LEFT JOIN (SELECT clothing_id, MAX(timestamp) AS last_laundry_timestamp FROM laundry GROUP BY clothing_id) as l\n " +
                "on o.clothing_id = l.clothing_id\n " +
                "where  o.timestamp>l.last_laundry_timestamp or l.last_laundry_timestamp is null)\n " +
                "on id=clothing_id\n " +
                "group by id"
    )
    fun get(): Flow<List<WornCount>>
}