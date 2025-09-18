package io.eldohub.feature.articles.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.eldohub.domain.article.model.Article
import io.eldohub.feature.articles.screen.viewmodels.ArticleViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateArticleScreen(
    viewModel: ArticleViewModel,
    onArticleSaved: () -> Unit,
    onBack: () -> Unit = {}
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isCompleted by remember { mutableStateOf(false) }
    var completionDateText by remember { mutableStateOf("") }

    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Article") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Mark as completed?")
                Switch(
                    checked = isCompleted,
                    onCheckedChange = { isCompleted = it }
                )
            }

            if (isCompleted) {
                OutlinedTextField(
                    value = completionDateText,
                    onValueChange = { completionDateText = it },
                    label = { Text("Completion Date (yyyy-MM-dd)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val isFormValid = title.isNotBlank() &&
                    content.isNotBlank() &&
                    (!isCompleted || completionDateText.isNotBlank())

            Button(
                onClick = {
                    val completionDate = if (isCompleted && completionDateText.isNotBlank()) {
                        try {
                            dateFormat.parse(completionDateText)
                        } catch (e: Exception) {
                            null
                        }
                    } else null

                    val article = Article(
                        id = 0L,
                        title = title,
                        content = content,
                        dateAdded = Date(),
                        dateCompleted = completionDate,
                        isCompleted = isCompleted
                    )
                    viewModel.addArticle(article)
                    onArticleSaved()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isFormValid
            ) {
                Text("Save Article")
            }
        }
    }
}
