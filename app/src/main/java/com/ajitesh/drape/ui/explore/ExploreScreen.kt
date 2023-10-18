package com.ajitesh.drape.ui.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Explore") })
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .padding(16.dp)) {
            Text(text = "Hey there!", fontSize = 24.sp)
        }
    }
}

@Preview
@Composable
fun PreviewExploreScreen(){
    ExploreScreen()
}