package io.eldohub.up2date

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.eldohub.core.ui.theme.Up2DateTheme
import io.eldohub.feature.newsfeed.navigation.NEWS_FEED_NAVIGATION
import io.eldohub.feature.newsfeed.navigation.newsFeedFeatureNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            Up2DateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NEWS_FEED_NAVIGATION
                    ) {

                        newsFeedFeatureNavGraph(navController = navController)

                    }
                }
            }
        }
    }
}