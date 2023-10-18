package com.ajitesh.drape.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajitesh.drape.data.datasource.local.entity.Clothing
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    uiState: DetailUiState,
    addToOutfit: (clothing: Clothing) -> Unit,
    addToLaundry: (clothing: Clothing) -> Unit,
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
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete")
                    }
                    IconButton(onClick = {
                        if (uiState is DetailUiState.OpenDetail)
                            addToOutfit(uiState.clothing)
                    }) {
                        Icon(imageVector = Icons.Outlined.Person, contentDescription = "Outfit")
                    }
                    IconButton(onClick = {
                        if (uiState is DetailUiState.OpenDetail)
                            addToLaundry(uiState.clothing)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = "Laundry"
                        )
                    }
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        expanded = false,
                        text = { Text(text = "Edit") },
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Edit"
                            )
                        },
                        onClick = {}
                    )
                }
            )
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
                    Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        DetailScreenImage(image = clothing.image)
                    }
                }
            } else {
                Text(text = "Detail Error")
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailScreenImage(image: String) {
    val imageSize = 300.dp
    if (image.isNotEmpty())
        GlideImage(
            modifier = Modifier.size(imageSize),
            model = image,
            contentScale = ContentScale.FillHeight,
            contentDescription = "Clothing Image"
        )
    else
        Box(
            modifier = Modifier
                .size(imageSize)
                .background(color = Color.Gray)
        )
}

@Preview
@Composable
fun PreviewDetailScreen() {
    val clothing = Clothing(id = -1, image = "")
    DetailScreen(
        uiState = DetailUiState.OpenDetail(clothing),
        addToOutfit = {},
        addToLaundry = {},
        popBackStack = {}
    )
}