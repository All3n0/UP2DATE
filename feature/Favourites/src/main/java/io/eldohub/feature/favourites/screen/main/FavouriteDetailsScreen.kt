package io.eldohub.feature.favourites.screen.main

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import io.eldohub.core.ui.theme.black
import io.eldohub.data.favorites.entity.FavoriteEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteDetailsScreen(
    article: FavoriteEntity,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favourite Details", color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
                    }
                },
                actions = {
                    val context = LocalContext.current

                    IconButton(onClick = {
                        article?.let {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_SUBJECT,
                                    it.title ?: "Check out this article"
                                )
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "${it.title}\n\nRead more here: ${it.url ?: ""}"
                                )
                            }
                            try {
                                context.startActivity(
                                    Intent.createChooser(shareIntent, "Share article via")
                                )
                            } catch (e: Exception) {
                                Log.e("NewsDetailsScreen", "Error while sharing", e)
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Share",
                            tint = black
                        )
                    }

                    IconButton(onClick = { /* since it’s already fav, you might remove */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Favourite", tint = Color.Red)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFFCDD2))
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            // Hero image
            if (!article.urlToImage.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(article.urlToImage),
                        contentDescription = article.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                                    startY = 200f
                                )
                            )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(24.dp)
            ) {
                Text(
                    text = article.title ?: "Untitled",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                if (!article.description.isNullOrEmpty()) {
                    Text(
                        text = article.description!!,
                        style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 22.sp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                if (!article.content.isNullOrEmpty()) {
                    Text(
                        text = article.content!!,
                        style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 22.sp)
                    )
                }

                Spacer(Modifier.height(20.dp))

                val context = LocalContext.current
                Button(
                    onClick = {
                        article.url?.let { url ->
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Log.e("FavouriteDetails", "Failed to open $url", e)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Read Full Article", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
