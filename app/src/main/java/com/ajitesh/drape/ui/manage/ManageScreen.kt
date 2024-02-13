package com.ajitesh.drape.ui.manage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun ManageScreen(
    tabUiState: TabUiState,
    updateTabUiState: (TabUiState) -> Unit,
    clothingList: List<Clothing>
) {

    val tabSelectedIndex = when (tabUiState) {
        is TabUiState.Fresh -> 0
        is TabUiState.Hanger -> 1
        is TabUiState.Basket -> 2
    }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Manage") })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            TabRow(selectedTabIndex = tabSelectedIndex) {
                Tab(
                    selected = tabUiState is TabUiState.Fresh,
                    onClick = { updateTabUiState(TabUiState.Fresh) },
                    text = { Text(text = "Fresh") }
                )
                Tab(
                    selected = tabUiState is TabUiState.Hanger,
                    onClick = { updateTabUiState(TabUiState.Hanger) },
                    text = { Text(text = "Hanger") }
                )
                Tab(
                    selected = tabUiState is TabUiState.Basket,
                    onClick = { updateTabUiState(TabUiState.Basket) },
                    text = { Text(text = "Laundry") }
                )
            }
            Box(modifier = Modifier.fillMaxSize()) {
                if (clothingList.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(80.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        content = {
                            items(clothingList) { clothing ->
                                GlideImage(
                                    model = clothing.image,
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f / 1.5f)
                                        .clip(shape = RoundedCornerShape(4.dp))
                                ) { requestBuilder ->
                                    requestBuilder.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                        .skipMemoryCache(true)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                } else {
                    val emptyText = when (tabSelectedIndex) {
                        0 -> "Seems like each piece of clothing has been worn multiple times."
                        1 -> "It appears that no clothes have been worn only once"
                        2 -> "There are no clothes that need to be laundered."
                        else -> ""
                    }
                    Text(
                        text = emptyText,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(36.dp),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewLaundryScreen() {
    ManageScreen(TabUiState.Fresh, {}, emptyList())
}