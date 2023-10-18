package com.ajitesh.drape.ui.manage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

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
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(80.dp),
                    verticalItemSpacing = 4.dp,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    content = {
                        items(clothingList) { clothing ->
                            GlideImage(
                                model = clothing.image,
                                contentScale = ContentScale.Fit,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .clip(shape = RoundedCornerShape(4.dp))
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewLaundryScreen() {
    ManageScreen(TabUiState.Fresh, {}, emptyList())
}