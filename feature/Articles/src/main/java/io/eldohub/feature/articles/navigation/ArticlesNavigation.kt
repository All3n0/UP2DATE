package io.eldohub.feature.articles.navigation

import ArticleListScreen
import androidx.activity.compose.BackHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import io.eldohub.core.ui.extensions.fromRightComposable
import io.eldohub.feature.articles.screen.main.ArticleDetailScreen
import io.eldohub.feature.articles.screen.viewmodels.ArticleViewModel
import org.koin.androidx.compose.koinViewModel

private const val ARTICLES_ROUTE = "articles/home_route"
private const val ARTICLE_DETAIL_ROUTE = "articles/detail_route"

fun NavGraphBuilder.articlesFeatureNavGraph(
    navController: NavController
) {
    // ✅ List Screen
    fromRightComposable(
        route = ARTICLES_ROUTE
    ) {
        BackHandler(onBack = {})
        val viewModel: ArticleViewModel = koinViewModel()
        ArticleListScreen(
            viewModel = viewModel,
            onArticleClick = { articleId ->
                navController.navigate("$ARTICLE_DETAIL_ROUTE/$articleId")
            },
            onCreateClick = {
                navController.navigate("articles/create_route")
            }
        )

    }

    // ✅ Detail Screen
    fromRightComposable(
        route = "$ARTICLE_DETAIL_ROUTE/{articleId}",
        arguments = listOf(
            navArgument("articleId") { type = NavType.LongType }
        )
    ) { backStackEntry ->
        val viewModel: ArticleViewModel = koinViewModel()
        val articleId = backStackEntry.arguments?.getLong("articleId") ?: 0L

        ArticleDetailScreen(
            viewModel = viewModel,
            articleId = articleId,
            onBack = { navController.popBackStack() },
//            onEdit = { id ->
//                navController.navigate("articles/edit_route/$id")
//            }
        )
    }
}
