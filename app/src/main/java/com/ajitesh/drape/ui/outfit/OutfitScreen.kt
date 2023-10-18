package com.ajitesh.drape.ui.outfit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajitesh.drape.data.datasource.local.entity.Outfit
import com.ajitesh.drape.groupByTimestamp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutfitScreen(uiState: OutfitUiState) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Outfit") })
        },
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            if (uiState is OutfitUiState.OutfitList) {
                if (uiState.outfits.isNotEmpty()) {
                    val outfitsMap = uiState.outfits.groupByTimestamp()
                    LazyColumn(
                        content = {
                            items(items = outfitsMap.entries.toList()) { entry ->
                                OutfitTile(entry)
                            }
                        })
                } else {
                    Text(modifier = Modifier.align(Alignment.Center), text = "Empty List")
                }
            } else {
                Text(text = "Error", fontSize = 24.sp)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun OutfitTile(entry: Map.Entry<LocalDate, List<Outfit>>) {
    Text(
        text = entry.key.toString(),
        fontWeight = FontWeight.Bold
    )
    Box(modifier = Modifier.size(4.dp))
    LazyRow(content = {
        items(entry.value.toList()) { outfit ->
            val imageModifier = Modifier
                .height(150.dp)
                .width(100.dp)
                .padding(4.dp)
            if (outfit.image.isNotEmpty())
                GlideImage(
                    modifier = imageModifier,
                    model = outfit.image,
                    contentDescription = "Outfit",
                    contentScale = ContentScale.Crop
                )
            else
                Box(
                    modifier = imageModifier.background(color = Color.Gray)
                )
        }
    })
    Box(modifier = Modifier.size(16.dp))
}

@Preview
@Composable
fun PreviewOutfitScreen() {
    val outfit = Outfit(clothingId = 1, image = "")
    OutfitScreen(uiState = OutfitUiState.OutfitList(mutableListOf(outfit,outfit,outfit)))
}