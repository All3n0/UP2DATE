package io.eldohub.feature.articles.navigation

import androidx.activity.compose.BackHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import io.eldohub.core.ui.extensions.fromRightComposable
import io.eldohub.feature.articles.ArticlesScreen

private const val ARTICLES_ROUTE = "articles/home_route"

fun NavGraphBuilder.articlesFeatureNavGraph(
    navController: NavController
) {
    fromRightComposable(
        route = ARTICLES_ROUTE
    ) {
        BackHandler(onBack = {})
        ArticlesScreen()
    }
}
