package io.eldohub.feature.articles.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.eldohub.core.ui.theme.primary100
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
                title = {
                    Box(
                        modifier = Modifier.drawBehind {
                            val strokeWidth = 4.dp.toPx() // thickness of underline
                            val yOffset = size.height + 6.dp.toPx() // push underline lower
                            drawLine(
                                color = primary100, // your custom underline color
                                start = Offset(0f, yOffset),
                                end = Offset(size.width, yOffset),
                                strokeWidth = strokeWidth
                            )
                        }
                    ) {
                        Text(
                            text = "Create Article",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = primary100
                        )
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
                label = {
                    Text(
                        "Title",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primary100,
                    unfocusedBorderColor = primary100
                )
            )

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = {
                    Text(
                        "Content",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primary100,
                    unfocusedBorderColor = primary100
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Mark as completed?",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = isCompleted,
                    onCheckedChange = { isCompleted = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = primary100,
                        checkedTrackColor = primary100.copy(alpha = 0.5f),
                        uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                    )
                )
            }

            if (isCompleted) {
                OutlinedTextField(
                    value = completionDateText,
                    onValueChange = { completionDateText = it },
                    label = {
                        Text(
                            "Completion Date (yyyy-MM-dd)",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primary100,
                        unfocusedBorderColor = primary100
                    )
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
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary100,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Save Article", fontWeight = FontWeight.Bold)
            }
        }

    }
}


