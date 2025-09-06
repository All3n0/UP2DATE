package io.eldohub.feature.favourites.navigation

import androidx.activity.compose.BackHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import io.eldohub.core.ui.extensions.fromRightComposable

import io.eldohub.feature.favourites.screen.main.FavouritesScreen

private const val FAVOURITES_ROUTE = "favourites/home_route"

fun NavGraphBuilder.favouritesFeatureNavGraph(
    navController: NavController
) {
    fromRightComposable(
        route = FAVOURITES_ROUTE
    ) {
        BackHandler(onBack = {})
        FavouritesScreen()
    }
}
