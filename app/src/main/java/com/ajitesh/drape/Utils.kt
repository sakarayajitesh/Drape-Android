package com.ajitesh.drape

import android.net.Uri
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.ajitesh.drape.data.datasource.local.entity.Outfit
import java.time.ZoneId
import java.util.Date

fun List<Uri>.toClothingList() = run {
    map {
        Clothing(image = it.toString())
    }
}

fun List<Outfit>.groupByTimestamp() = run {
    this.groupBy{
        Date(it.timestamp).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }
}