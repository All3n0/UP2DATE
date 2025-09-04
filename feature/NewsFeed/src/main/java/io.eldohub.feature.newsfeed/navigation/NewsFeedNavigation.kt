package io.eldohub.feature.newsfeed

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import io.eldohub.feature.articles.ArticlesScreen
import io.eldohub.feature.favourites.FavouritesScreen
import io.eldohub.feature.newsfeed.screen.NewsFeedScreen
import kotlinx.coroutines.launch

private const val TOTAL_TABS = 3
const val NEWS_FEED_NAVIGATION = "newsfeed/newsfeed_navigation"

fun NavController.navigateToNewsFeedRoot() = navigate(NEWS_FEED_NAVIGATION) {
    popUpTo(NEWS_FEED_NAVIGATION) { inclusive = true }
}

@OptIn(ExperimentalPagerApi::class)
fun NavGraphBuilder.newsFeedNavGraph(navController: NavController) {
    composable(route = NEWS_FEED_NAVIGATION) {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        BackHandler(onBack = {})

        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                NewsFeedBottomNav(pagerState = pagerState) { page ->
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
                    0 -> NewsFeedScreen()
                    1 -> FavouritesScreen()
                    2 -> ArticlesScreen()
                }
            }
        }
    }
}
