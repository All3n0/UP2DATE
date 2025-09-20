import androidx.activity.compose.BackHandler
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import io.eldohub.core.ui.extensions.fromRightComposable
import io.eldohub.feature.favourites.screen.main.FavouritesScreen
import io.eldohub.feature.favourites.screen.viewmodels.FavoriteViewModel

private const val FAVOURITES_ROUTE = "favourites/home_route"

fun NavGraphBuilder.favouritesFeatureNavGraph(
    navController: NavController
) {
    fromRightComposable(
        route = FAVOURITES_ROUTE
    ) {
        BackHandler(onBack = {})

        val favViewModel: FavoriteViewModel = viewModel()
        FavouritesScreen(viewModel = favViewModel)
    }
}
