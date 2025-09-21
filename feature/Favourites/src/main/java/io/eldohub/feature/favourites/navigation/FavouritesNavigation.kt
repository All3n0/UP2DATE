import androidx.activity.compose.BackHandler
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.eldohub.core.ui.extensions.fromRightComposable
import io.eldohub.feature.favourites.screen.main.FavouritesScreen
import io.eldohub.feature.favourites.screen.main.FavouriteDetailsScreen
import io.eldohub.feature.favourites.screen.viewmodels.FavoriteViewModel
private const val FAVOURITES_HOME_ROUTE = "favourites/home_route"
private const val FAVOURITES_DETAIL_ROUTE = "favourites/detail/{favId}"

fun NavGraphBuilder.favouritesFeatureNavGraph(
    navController: NavController
) {
    // Home screen
    fromRightComposable(
        route = FAVOURITES_HOME_ROUTE
    ) {
        BackHandler(onBack = {})

        val favViewModel: FavoriteViewModel = viewModel()
        FavouritesScreen(
            viewModel = favViewModel,
            onArticleClick = { favEntity ->
                navController.navigate("favourites/detail/${favEntity.id}")
            }
        )
    }

    // Details screen
    composable(
        route = FAVOURITES_DETAIL_ROUTE,
        arguments = listOf(navArgument("favId") { type = NavType.LongType })
    ) { backStackEntry ->
        val favId = backStackEntry.arguments?.getLong("favId") ?: return@composable
        val favViewModel: FavoriteViewModel = viewModel()

        val favorite = favViewModel.favorites.collectAsState().value
            .find { it.id.toLong() == favId }

        favorite?.let {
            FavouriteDetailsScreen(
                article = it,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
