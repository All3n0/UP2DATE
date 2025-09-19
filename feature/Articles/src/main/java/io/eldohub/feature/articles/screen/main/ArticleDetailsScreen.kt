package io.eldohub.feature.articles.screen.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.eldohub.core.ui.theme.primary100
import io.eldohub.feature.articles.screen.viewmodels.ArticleViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    viewModel: ArticleViewModel,
    articleId: Long,
    onBack: () -> Unit
) {
    val article by viewModel.selectedArticle.collectAsState()
    var isEditing by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Editable fields
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isCompleted by remember { mutableStateOf(false) }
    var completionDateText by remember { mutableStateOf("") }
    var isCompletionDateValid by remember { mutableStateOf(true) }

    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    LaunchedEffect(articleId) {
        viewModel.loadArticleById(articleId)
    }

    LaunchedEffect(article) {
        article?.let {
            title = it.title
            content = it.content
            isCompleted = it.isCompleted
            completionDateText = it.dateCompleted?.let { date -> dateFormat.format(date) } ?: ""
        }
    }

    // Validate completion date whenever it changes
    LaunchedEffect(isCompleted, completionDateText) {
        isCompletionDateValid = if (isCompleted) {
            try {
                dateFormat.parse(completionDateText)
                true
            } catch (e: Exception) {
                false
            }
        } else true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.drawBehind {
                            val strokeWidth = 4.dp.toPx()
                            val yOffset = size.height + 6.dp.toPx()
                            drawLine(
                                color = primary100,
                                start = Offset(0f, yOffset),
                                end = Offset(size.width, yOffset),
                                strokeWidth = strokeWidth
                            )
                        }
                    ) {
                        Text(
                            text = "Article Details",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        article?.let { art ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                if (isEditing) {
                    // Title field
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primary100,
                            unfocusedBorderColor = primary100
                        )
                    )

                    // Content field
                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Content", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primary100,
                            unfocusedBorderColor = primary100
                        )
                    )

                    // Completion switch
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Mark as completed?",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Switch(
                            checked = isCompleted,
                            onCheckedChange = { isCompleted = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = primary100,
                                checkedTrackColor = primary100.copy(alpha = 0.5f)
                            )
                        )
                    }

                    if (isCompleted) {
                        OutlinedTextField(
                            value = completionDateText,
                            onValueChange = { completionDateText = it },
                            label = { Text("Completion Date (yyyy-MM-dd)", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primary100,
                                unfocusedBorderColor = primary100
                            )
                        )
                        if (!isCompletionDateValid) {
                            Text(
                                "Invalid completion date",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                } else {
                    // Display article normally
                    Text(art.title, style = MaterialTheme.typography.headlineSmall)
                    Text(art.content, style = MaterialTheme.typography.bodyLarge)
                    if (art.isCompleted) {
                        Text(
                            "Completed on: ${art.dateCompleted?.let { dateFormat.format(it) } ?: "-"}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Save/Edit button
                    Button(
                        onClick = {
                            if (isEditing) {
                                val completionDate = if (isCompleted && completionDateText.isNotBlank()) {
                                    try { dateFormat.parse(completionDateText) } catch (e: Exception) { null }
                                } else null

                                val updatedArticle = art.copy(
                                    title = title,
                                    content = content,
                                    isCompleted = isCompleted,
                                    dateCompleted = completionDate
                                )
                                viewModel.saveArticleEdits(updatedArticle)

                                // Immediately update UI
                                title = updatedArticle.title
                                content = updatedArticle.content
                                isCompleted = updatedArticle.isCompleted
                                completionDateText = updatedArticle.dateCompleted?.let { dateFormat.format(it) } ?: ""
                            }
                            isEditing = !isEditing
                        },
                        enabled = !isCompleted || isCompletionDateValid,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary100,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(if (isEditing) "Save" else "Edit")
                    }

                    // Mark Complete button
//                    Button(
//                        enabled = !art.isCompleted,
//                        onClick = { viewModel.markAsComplete(art.id) }
//                    ) {
//                        Text("Mark Complete")
//                    }

                    // Delete button
                    OutlinedButton(
                        onClick = { showDeleteDialog = true },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text("Delete")
                    }
                }

                // Delete confirmation dialog
                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("Confirm Delete") },
                        text = { Text("Are you sure you want to delete this article?") },
                        confirmButton = {
                            TextButton(onClick = {
                                viewModel.deleteArticle(art.id)
                                showDeleteDialog = false
                                onBack()
                            }) {
                                Text("Yes")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDeleteDialog = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
