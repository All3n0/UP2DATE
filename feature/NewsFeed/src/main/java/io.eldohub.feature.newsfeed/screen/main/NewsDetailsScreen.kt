package io.eldohub.feature.newsfeed.screen.main

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
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import io.eldohub.domain.newsFeed.model.Article

// Define the color scheme
val primary100 = Color(0xFFFFCDD2) // Light red
val primaryDark = Color(0xFFD32F2F) // Darker red for contrast
val black = Color(0xFF000000)
val white = Color(0xFFFFFFFF)
val gray100 = Color(0xFFF5F5F5)
val gray800 = Color(0xFF424242)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailsScreen(
    article: Article?,
    onBack: () -> Unit
) {
    Log.d("NewsDetailsScreen", "Recomposed with article: $article")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "News Details",
                        color = black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primary100
                ),
                actions = {
                    IconButton(onClick = { /* Handle share */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Share",
                            tint = black
                        )
                    }
                    IconButton(onClick = { /* Handle bookmark */ }) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Bookmark",
                            tint = black
                        )
                    }
                }
            )
        },
        containerColor = gray100
    ) { padding ->
        if (article == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = primaryDark)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
            ) {
                // Hero image with gradient overlay
                if (!article.urlToImage.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(article.urlToImage),
                            contentDescription = article.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )

                        // Gradient overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            black.copy(alpha = 0.7f)
                                        ),
                                        startY = 300f
                                    )
                                )
                        )

                        // Source badge on image
                        if (!article.source?.name.isNullOrEmpty()) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(16.dp)
                                    .background(
                                        color = primary100,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = article.source?.name ?: "Unknown Source",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = black,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                // Content area
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = white,
                            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                        )
                        .padding(24.dp)
                ) {
                    // Title
                    Text(
                        text = article.title ?: "Untitled",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = black,
                            fontSize = 24.sp
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Author + Date Row
                    // Author + Date Section with Icons
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        // Author section
                        if (!article.author.isNullOrEmpty()) {
                            Row(
                                modifier = Modifier.padding(bottom = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.AccountCircle,
                                    contentDescription = "Author",
                                    tint = primaryDark,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = article.author!!,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = gray800,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }

                        // Date section
                        if (!article.publishedAt.isNullOrEmpty()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.CheckCircle,
                                    contentDescription = "Published",
                                    tint = primaryDark,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = formatDate(article.publishedAt!!),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = gray800
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Divider
                    Divider(
                        color = primary100.copy(alpha = 0.5f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Description
                    if (!article.description.isNullOrEmpty()) {
                        Text(
                            text = "Summary",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = black
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = article.description ?: "",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = gray800,
                                lineHeight = 22.sp
                            ),
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }

                    // Full Content
                    if (!article.content.isNullOrEmpty()) {
                        Text(
                            text = "Full Story",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = black
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = article.content ?: "",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = gray800,
                                lineHeight = 22.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Read more button
                    val context = LocalContext.current

                    Button(
                        onClick = {
                            article.url?.let { url ->
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Log.e("NewsDetailsScreen", "Failed to open URL: $url", e)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryDark,
                            contentColor = white
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Read Full Article",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}private fun formatDate(dateString: String): String {
    return try {
        // Simple formatting - you might want to use a proper date formatter
        if (dateString.length > 10) {
            dateString.substring(0, 10) // Just show YYYY-MM-DD
        } else {
            dateString
        }
    } catch (e: Exception) {
        dateString
    }
}