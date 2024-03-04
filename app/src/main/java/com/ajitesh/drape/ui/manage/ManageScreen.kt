package com.ajitesh.drape.ui.manage

import android.os.Looper
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
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
    clothingList: List<Clothing>,
    selectedClothingList: List<Int>,
    selectClothing: (Int) -> Unit,
    addToLaundry: (() -> Unit) -> Unit
) {

    val tabSelectedIndex = when (tabUiState) {
        is TabUiState.Fresh -> 0
        is TabUiState.Hanger -> 1
        is TabUiState.Basket -> 2
    }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Manage") },
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = selectedClothingList.isNotEmpty(),
                enter = slideInVertically(
                    animationSpec = tween(
                        durationMillis = 200,
                        delayMillis = 300
                    )
                ) { it },
                exit = fadeOut() + scaleOut()
            ) {
                BottomAppBar(contentPadding = PaddingValues(0.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val message = "${selectedClothingList.size} clothes selected"
                        Text(
                            text = message,
                            fontSize = 14.sp,
                        )
                        if (selectedClothingList.isNotEmpty()) {
                            Box(modifier = Modifier.width(16.dp))
                            Button(onClick = {
                                addToLaundry {
                                    android.os.Handler(Looper.getMainLooper()).post {
                                        Toast.makeText(
                                            context,
                                            "Added to Laundry",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }

                                }
                            }) {
                                Text(text = "Add to laundry")
                            }
                        }
                    }
                }
            }
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
                    text = { Text(text = "Basket") }
                )
            }
            Box(modifier = Modifier.fillMaxSize()) {
                if (clothingList.isNotEmpty()) {
                    Column {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(80.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            content = {
                                items(clothingList) { clothing ->
                                    val isSelected = selectedClothingList.contains(clothing.id)

                                    Box {
                                        GlideImage(
                                            model = clothing.image,
                                            contentScale = ContentScale.Crop,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .aspectRatio(1f / 1.5f)
                                                .clip(shape = RoundedCornerShape(4.dp))
                                                .clickable {
                                                    selectClothing(clothing.id)
                                                }
                                        ) { requestBuilder ->
                                            requestBuilder.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                                .skipMemoryCache(true)
                                        }

                                        if (isSelected) {
                                            Box(
                                                modifier = Modifier
                                                    .matchParentSize()
                                                    .clip(shape = RoundedCornerShape(4.dp))
                                                    .background(color = Color.Black.copy(alpha = 0.6f))
                                            )
                                            Icon(
                                                Icons.Filled.Check,
                                                contentDescription = "Selected",
                                                tint = Color.White,
                                                modifier = Modifier.align(Alignment.Center)
                                            )
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .weight(1f)
                        )
                        AnimatedVisibility(
//                            visible = selectedClothingList.isEmpty(),
                            visible = false,
                            enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
                                -fullWidth / 3
                            } + fadeIn(animationSpec = tween(200)),
                            exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
                                200
                            } + fadeOut()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                            ) {
                                Text(
                                    text = "Select clothes to do laundry",
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
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