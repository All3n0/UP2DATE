package io.eldohub.feature.newsfeed.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import io.eldohub.core.ui.theme.*

enum class NewsFeedPages(val icon: ImageVector, val label: String) {
    NEWS_FEED(Icons.Default.Home, "Home"),
    FAVOURITES(Icons.Default.Favorite, "Favourites"),
    ARTICLES(Icons.Default.Create, "Articles")
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedBottomNav(
    pagerState: PagerState,
    onPageSelected: (NewsFeedPages) -> Unit
) {
    NavigationBar(
        containerColor = white,
        tonalElevation = 6.dp
    ) {
        NewsFeedPages.values().forEachIndexed { index, page ->
            val selected = pagerState.currentPage == index
            NavigationBarItem(
                selected = selected,
                onClick = { onPageSelected(page) },
                icon = {
                    Icon(
                        imageVector = page.icon,
                        contentDescription = page.label,
                        tint = if (selected) primary100 else grey50
                    )
                },
                label = {
                    Text(
                        text = page.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (selected) primary100 else grey50
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = primary05
                )
            )
        }
    }
}
