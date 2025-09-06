package io.eldohub.feature.newsfeed.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

enum class NewsFeedPages {
    NEWS_FEED, FAVOURITES, ARTICLES
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedBottomNav(
    pagerState: PagerState,
    onPageSelected: (NewsFeedPages) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = pagerState.currentPage == 0,
            onClick = { onPageSelected(NewsFeedPages.NEWS_FEED) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "News Feed"
                )
            }
        )
        NavigationBarItem(
            selected = pagerState.currentPage == 1,
            onClick = { onPageSelected(NewsFeedPages.FAVOURITES) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favourites"
                )
            }
        )
        NavigationBarItem(
            selected = pagerState.currentPage == 2,
            onClick = { onPageSelected(NewsFeedPages.ARTICLES) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Articles"
                )
            }
        )
    }
}