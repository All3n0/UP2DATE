// navigation/NewsFeedNavigation.kt
package io.eldohub.feature.newsfeed.navigation

import ArticleListScreen
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import io.eldohub.feature.articles.screen.main.ArticleDetailScreen
import io.eldohub.feature.articles.screen.main.CreateArticleScreen
import io.eldohub.feature.articles.screen.viewmodels.ArticleViewModel
import io.eldohub.feature.favourites.screen.main.FavouritesScreen
import io.eldohub.feature.newsfeed.screen.main.NewsDetailsScreen
import io.eldohub.feature.newsfeed.screen.main.NewsFeedScreen
import io.eldohub.feature.newsfeed.screen.viewmodels.NewsDetailsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private const val TOTAL_TABS = 3
const val NEWS_FEED_NAVIGATION = "newsfeed/newsfeed_navigation"
const val ARTICLE_DETAIL_ROUTE = "articles/detail_route/{articleId}"
const val CREATE_ARTICLE_ROUTE = "articles/create_route"
const val NEWS_DETAILS_ROUTE = "news/details"

@OptIn(ExperimentalPagerApi::class)
fun NavGraphBuilder.newsFeedFeatureNavGraph(
    navController: NavController
) {
    composable(route = NEWS_FEED_NAVIGATION) { navBackStackEntry ->
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        // 👇 Get one instance of NewsDetailsViewModel tied to this NavGraph
        val newsDetailsViewModel: NewsDetailsViewModel =
            koinViewModel(viewModelStoreOwner = navBackStackEntry)
        BackHandler(onBack = {})

        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                NewsFeedBottomNav(
                    pagerState = pagerState
                ) { page ->
                    scope.launch {
                        when (page) {
                            NewsFeedPages.NEWS_FEED -> pagerState.scrollToPage(0)
                            NewsFeedPages.FAVOURITES -> pagerState.scrollToPage(1)
                            NewsFeedPages.ARTICLES -> pagerState.scrollToPage(2)
                        }
                    }
                }
            }
        ) { contentPadding ->
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(contentPadding),
                count = TOTAL_TABS,
                state = pagerState,
                verticalAlignment = Alignment.Top,
                userScrollEnabled = false
            ) { position ->
                when (position) {
                    0 -> {
                        NewsFeedScreen(onClick = { article ->
                            newsDetailsViewModel.setArticle(article)
                            navController.navigate(NEWS_DETAILS_ROUTE)
                        })
                    }
                    1 -> FavouritesScreen()
                    2 -> {
                        val articleViewModel: ArticleViewModel = koinViewModel()
                        ArticleListScreen(
                            viewModel = articleViewModel,
                            onArticleClick = { articleId ->
                                navController.navigate("articles/detail_route/$articleId")
                            },
                            onCreateClick = {
                                navController.navigate(CREATE_ARTICLE_ROUTE)
                            }
                        )
                    }
                }
            }
        }
    }

    // ✅ Create Article destination
    composable(route = CREATE_ARTICLE_ROUTE) {
        val articleViewModel: ArticleViewModel = koinViewModel()
        CreateArticleScreen(
            viewModel = articleViewModel,
            onArticleSaved = {
                navController.popBackStack()
            }
        )
    }

    // ✅ Article detail route
    composable(
        route = ARTICLE_DETAIL_ROUTE,
        arguments = listOf(navArgument("articleId") { type = NavType.LongType })
    ) { backStackEntry ->
        val articleId = backStackEntry.arguments?.getLong("articleId") ?: return@composable
        val articleViewModel: ArticleViewModel = koinViewModel()
        ArticleDetailScreen(
            viewModel = articleViewModel,
            articleId = articleId,
            onBack = { navController.popBackStack() }
        )
    }

    // ✅ News details route uses the SAME ViewModel
    composable(route = NEWS_DETAILS_ROUTE) { navBackStackEntry ->
        // ✅ Use the parent route as the owner so we reuse the same instance
        val parentEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(NEWS_FEED_NAVIGATION)
        }
        val newsDetailsViewModel: NewsDetailsViewModel =
            koinViewModel(viewModelStoreOwner = parentEntry)

        val article by newsDetailsViewModel.article.collectAsState()

        NewsDetailsScreen(
            article = article,
            onBack = { navController.popBackStack() }
        )
    }


}
