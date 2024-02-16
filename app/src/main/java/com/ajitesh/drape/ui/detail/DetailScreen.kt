package com.ajitesh.drape.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajitesh.drape.R
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.ajitesh.drape.getDateFromTimeStamp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    uiState: DetailUiState,
    addToOutfit: (clothing: Clothing) -> Unit,
    addToLaundry: () -> Unit,
    delete: (() -> Unit) -> Unit,
    popBackStack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail") },
                navigationIcon = {
                    IconButton(onClick = { popBackStack() }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    FilledTonalIconButton(onClick = {
                        delete(popBackStack)
                    }) {
                        Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete")
                    }
                }
            )
        },
        bottomBar = {
            if (uiState is DetailUiState.OpenDetail)
                DetailScreenBottomAppBar(uiState.clothing, addToOutfit, addToLaundry)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (uiState is DetailUiState.OpenDetail) {
                val clothing = uiState.clothing
                Column(modifier = Modifier.fillMaxSize()) {
                    DetailScreenImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f / 1.5f)
                            .align(Alignment.CenterHorizontally),
                        image = clothing.image
                    )
                    Box(modifier = Modifier.height(16.dp))
//                    Card {
//                        Column(
//                            modifier = Modifier
//                                .padding(vertical = 8.dp, horizontal = 16.dp),
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Text(
//                                text = clothing.wearLimit.toString(),
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 24.sp
//                            )
//                            Text(text = "Wear Limit", fontSize = 12.sp)
//                        }
//
//                    }

                    if (uiState.laundryCount != null && uiState.laundryCount > 0) {
                        Box(modifier = Modifier.height(16.dp))
                        DetailScreenLaundry(
                            modifier = Modifier.fillMaxWidth(),
                            laundryCount = uiState.laundryCount,
                            lastLaundryDate = uiState.lastLaundryDate
                        )
                    }
                }
            } else {
                Text(text = "Detail Error")
            }
        }
    }
}

@Composable
fun DetailScreenBottomAppBar(
    clothing: Clothing,
    addToOutfit: (clothing: Clothing) -> Unit,
    addToLaundry: () -> Unit
) {
    BottomAppBar(
        actions = {
            FilledTonalButton(onClick = {
                addToOutfit(clothing)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.outfit),
                    contentDescription = "Add to outfit"
                )
                Box(modifier = Modifier.width(4.dp))
                Text(text = "Add to outfit")
            }
            Box(modifier = Modifier.width(16.dp))
            FilledTonalButton(onClick = {
                addToLaundry()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.laundry),
                    contentDescription = "Add to laundry"
                )
                Box(modifier = Modifier.width(4.dp))
                Text(text = "Add to laundry")
            }
        },
//        floatingActionButton = {
//            ExtendedFloatingActionButton(
//                expanded = false,
//                text = { Text(text = "Edit") },
//                icon = {
//                    Icon(
//                        imageVector = Icons.Outlined.Edit,
//                        contentDescription = "Edit"
//                    )
//                },
//                onClick = {}
//            )
//        }
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun DetailScreenImage(modifier: Modifier = Modifier, image: String) {
    if (image.isNotEmpty())
        GlideImage(
            modifier = modifier.clip(shape = RoundedCornerShape(4.dp)),
            model = image,
            contentScale = ContentScale.Crop,
            contentDescription = "Clothing Image"
        )
    else
        Box(
            modifier = modifier
                .clip(shape = RoundedCornerShape(4.dp))
                .background(color = Color.Gray)
        )
}

@Composable
private fun DetailScreenLaundry(modifier: Modifier, laundryCount: Int, lastLaundryDate: Long?) {
    val mLastLaundryDate =
        if (lastLaundryDate != null) getDateFromTimeStamp(lastLaundryDate) else ""
    Card(modifier = modifier, shape = RoundedCornerShape(4.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = laundryCount.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(text = "Total Laundry", fontSize = 12.sp)
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = mLastLaundryDate,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(text = "Last Laundry", fontSize = 12.sp)
            }
        }
    }
}

@Preview
@Composable
fun PreviewDetailScreen() {
    val clothing = Clothing(id = -1, image = "")
    DetailScreen(
        uiState = DetailUiState.OpenDetail(clothing),
        addToOutfit = {},
        addToLaundry = {},
        delete = {},
        popBackStack = {}
    )
}