package com.ajitesh.drape.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ajitesh.drape.ui.detail.DetailScreen
import com.ajitesh.drape.ui.detail.DetailViewModel
import com.ajitesh.drape.ui.home.HomeScreen
import com.ajitesh.drape.ui.home.HomeViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val viewModel = hiltViewModel<HomeViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            HomeScreen(
                uiState = uiState,
                updateUiState = viewModel::updateUiState,
                navigate = navController::navigate
            )
        }
        composable(
            "detail/{clothingId}",
            arguments = listOf(navArgument("clothingId") { type = NavType.IntType })
        ) {
            val viewModel = hiltViewModel<DetailViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            DetailScreen(
                uiState,
                viewModel::addToOutfit,
                viewModel::addToLaundry,
                viewModel::deleteClothing,
                navController::popBackStack
            )
        }
    }
}

@Preview
@Composable
fun PreviewSecondMainScreen() {
    MainScreen()
}