package com.ajitesh.drape.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ajitesh.drape.data.datasource.local.WornCount
import kotlinx.coroutines.flow.Flow

@Dao
fun interface MasterDao {

    @Query(
        "WITH A as (select c.*, o.timestamp as wear, l.last_laundry, case when o.timestamp < l.last_laundry then 0 else 1 end as worn_count from clothing as c left join outfit as o on c.id=o.clothing_id left join (SELECT clothing_id, max(timestamp) as last_laundry from laundry group by clothing_id) as l on l.clothing_id=o.clothing_id) \n" +
                "select A.id, A.image,  sum(A.worn_count) as worn_count from A group by A.id"
    )
    fun get(): Flow<List<WornCount>>
}