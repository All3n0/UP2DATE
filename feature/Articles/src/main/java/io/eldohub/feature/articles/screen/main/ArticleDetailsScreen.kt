package io.eldohub.feature.articles.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.eldohub.feature.articles.screen.viewmodels.ArticleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    viewModel: ArticleViewModel,
    articleId: Long,
    onBack: () -> Unit
) {
    val article by viewModel.selectedArticle.collectAsState()

    // Load article when this screen is entered
    LaunchedEffect(articleId) {
        viewModel.loadArticleById(articleId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Article Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        article?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(it.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(12.dp))
                Text(it.content, style = MaterialTheme.typography.bodyLarge)
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
