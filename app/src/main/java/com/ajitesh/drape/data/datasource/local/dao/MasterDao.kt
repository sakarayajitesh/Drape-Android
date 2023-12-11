package com.ajitesh.drape.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ajitesh.drape.data.datasource.local.WornCount
import kotlinx.coroutines.flow.Flow

@Dao
fun interface MasterDao {

//    @Query(
//        "WITH A as (select c.*, o.timestamp as wear, l.last_laundry, case when o.timestamp < l.last_laundry then 0 else 1 end as worn_count from clothing as c left join outfit as o on c.id=o.clothing_id left join (SELECT clothing_id, max(timestamp) as last_laundry from laundry group by clothing_id) as l on l.clothing_id=o.clothing_id) \n" +
//                "select A.id, A.image,  sum(A.worn_count) as worn_count from A group by A.id"
//    )
    @Query("SELECT i.*, COUNT(o.id) AS worn_count\n" +
            "    FROM Clothing AS i\n" +
            "    LEFT JOIN outfit AS o ON i.id = o.clothing_id\n" +
            "    LEFT JOIN (\n" +
            "      SELECT id, MAX(timestamp) AS last_laundry_timestamp\n" +
            "      FROM laundry\n" +
            "      GROUP BY clothing_id\n" +
            "    ) AS l ON i.id = l.id\n" +
            "    WHERE o.timestamp > l.last_laundry_timestamp OR l.last_laundry_timestamp IS NULL\n" +
            "    GROUP BY i.id")
    fun get(): Flow<List<WornCount>>
}