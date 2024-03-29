package com.ajitesh.drape.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajitesh.drape.ui.closet.ClosetScreen
import com.ajitesh.drape.ui.closet.ClosetViewModel
import com.ajitesh.drape.ui.explore.ExploreScreen
import com.ajitesh.drape.ui.manage.ManageScreen
import com.ajitesh.drape.ui.manage.ManageViewModel
import com.ajitesh.drape.ui.outfit.OutfitScreen
import com.ajitesh.drape.ui.outfit.OutfitViewModel


@Composable
fun HomeScreen(
    uiState: HomeUiState,
    updateUiState: (HomeUiState) -> Unit,
    navigate: (String) -> Unit
) {
    val navController: NavHostController = rememberNavController()
    var navBarVisible by remember { mutableStateOf(true) }
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = navBarVisible,
                enter = slideInVertically(
                    animationSpec = tween(
                        durationMillis = 200
                    )
                ) { it },
                exit = fadeOut()
            ) {
                NavigationBar {
                    HomeUiStateList.forEachIndexed { _, item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.title
                                )
                            },
                            label = { Text(item.title) },
                            selected = HomeUiStateList[uiState.position].title == item.title,
                            onClick = {
                                updateUiState(item)
                                navController.navigate(item.title) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = HomeUiStateList[uiState.position].title,
        ) {
            composable(HomeUiState.Explore.title) {
                ExploreScreen()
            }
            composable(HomeUiState.Outfit.title) {
                val viewModel = hiltViewModel<OutfitViewModel>()
                val outfitUiState by viewModel.uiState.collectAsState()
                OutfitScreen(uiState = outfitUiState)
            }
            composable(HomeUiState.Closet.title) {
                val viewModel = hiltViewModel<ClosetViewModel>()
                val closetUiState by viewModel.uiState.collectAsState()
                ClosetScreen(
                    uiState = closetUiState,
                    addPhotos = viewModel::addPhotos,
                    navigate = navigate
                )
            }
            composable(HomeUiState.Manage.title) {
                val viewModel = hiltViewModel<ManageViewModel>()
                val tabUiState by viewModel.tabUiState.collectAsState()
                val clothingList by viewModel.clothingList.collectAsState(initial = emptyList())
                val selectedClothing by viewModel.selectedClothing.collectAsState()
                navBarVisible = selectedClothing.isEmpty()
                ManageScreen(
                    tabUiState = tabUiState,
                    clothingList = clothingList,
                    selectedClothingList = selectedClothing,
                    updateTabUiState = viewModel::updateTabUiState,
                    selectClothing = viewModel::selectClothing,
                    addToLaundry = viewModel::addToLaundry
                )
            }
        }
    }
}