package io.eldohub.feature.articles.screen.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.eldohub.domain.article.model.Article
import io.eldohub.feature.articles.screen.viewmodels.ArticleViewModel

@Composable
fun ArticleListScreen(
    viewModel: ArticleViewModel,
    onArticleClick: (Long) -> Unit
) {
    val articles by viewModel.articles.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Top Headlines",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (articles.isEmpty()) {
            Text("No articles yet", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(articles) { article ->
                    ArticleItem(article = article, onClick = { onArticleClick(article.id) })
                }
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(article.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(article.content.take(100) + "...", style = MaterialTheme.typography.bodySmall)
        }
    }
}
