package io.eldohub.feature.newsfeed.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import io.eldohub.feature.articles.screen.main.ArticleListScreen
import io.eldohub.feature.articles.screen.viewmodels.ArticleViewModel
import io.eldohub.feature.favourites.screen.main.FavouritesScreen
import io.eldohub.feature.newsfeed.screen.main.NewsFeedScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private const val TOTAL_TABS = 3
const val NEWS_FEED_NAVIGATION = "newsfeed/newsfeed_navigation"

@OptIn(ExperimentalPagerApi::class)
fun NavGraphBuilder.newsFeedFeatureNavGraph(
    navController: NavController
) {
    composable(route = NEWS_FEED_NAVIGATION) {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

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
                    0 -> NewsFeedScreen(onClick = {})  // your news feed
                    1 -> FavouritesScreen()
                    2 -> {
                        val articleViewModel: ArticleViewModel = koinViewModel()
                        ArticleListScreen(
                            viewModel = articleViewModel,
                            onArticleClick = { articleId ->
                                navController.navigate("articles/detail_route/$articleId")
                            }
                        )
                    }
                }
            }
        }
    }
}