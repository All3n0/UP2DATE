package io.eldohub.feature.articles.screen.main

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import io.eldohub.core.ui.theme.primary100
import io.eldohub.feature.articles.screen.viewmodels.ArticleViewModel
import io.eldohub.feature.articles.util.saveBitmapToInternalStorage
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

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isCompleted by remember { mutableStateOf(false) }
    var completionDateText by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<String?>(null) }
    var isCompletionDateValid by remember { mutableStateOf(true) }

    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { imageUri = it.toString() }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val savedUri = saveBitmapToInternalStorage(context, it)
            imageUri = savedUri.toString()
        }
    }

    LaunchedEffect(articleId) {
        viewModel.loadArticleById(articleId)
    }

    LaunchedEffect(article) {
        article?.let {
            title = it.title
            content = it.content
            isCompleted = it.isCompleted
            completionDateText = it.dateCompleted?.let { date -> dateFormat.format(date) } ?: ""
            imageUri = it.imageUri
        }
    }

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
                title = { Text("Article Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack, colors = IconButtonDefaults.iconButtonColors(
                        contentColor = primary100
                    )) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isEditing) {
                            val completionDate = if (isCompleted && completionDateText.isNotBlank()) {
                                try { dateFormat.parse(completionDateText) } catch (e: Exception) { null }
                            } else null

                            val updatedArticle = article?.copy(
                                title = title,
                                content = content,
                                isCompleted = isCompleted,
                                dateCompleted = completionDate,
                                imageUri = imageUri
                            )
                            updatedArticle?.let { viewModel.saveArticleEdits(it) }
                        }
                        isEditing = !isEditing
                    },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = primary100)
                    ) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = if (isEditing) "Save" else "Edit"
                        )
                    }

                    IconButton(onClick = { showDeleteDialog = true },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Red)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (imageUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(imageUri),
                                contentDescription = "Selected image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Text("No image selected")
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { galleryLauncher.launch("image/*") },
                                colors = IconButtonDefaults.iconButtonColors(contentColor = primary100)
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "Pick from gallery")
                            }
                            IconButton(onClick = { cameraLauncher.launch(null) },
                                colors = IconButtonDefaults.iconButtonColors(contentColor = primary100)
                            ) {
                                Icon(Icons.Default.CameraAlt, contentDescription = "Take photo")
                            }
                        }
                    }
                } else {
                    imageUri?.let { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = "Article image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                if (isEditing) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title", fontWeight = FontWeight.Bold, color = Color.Black) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Red,
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Content", fontWeight = FontWeight.Bold, color = Color.Black) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Red,
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Mark as completed?", fontWeight = FontWeight.Bold, color = Color.Black)
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
                            label = { Text("Completion Date (yyyy-MM-dd)", fontWeight = FontWeight.Bold, color = Color.Black) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Red,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        if (!isCompletionDateValid) {
                            Text("Invalid completion date", color = Color.Red)
                        }
                    }
                } else {
                    Text(art.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(art.content, style = MaterialTheme.typography.bodyLarge)
                    if (art.isCompleted) {
                        Text("Completed on: ${art.dateCompleted?.let { dateFormat.format(it) } ?: "-"}")
                    }
                }

                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("Confirm Delete", fontWeight = FontWeight.Bold) },
                        text = { Text("Are you sure you want to delete this article?") },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    viewModel.deleteArticle(art.id)
                                    showDeleteDialog = false
                                    onBack()
                                },
                                colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                            ) { Text("Yes", fontWeight = FontWeight.Bold) }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showDeleteDialog = false },
                                colors = ButtonDefaults.textButtonColors(contentColor = primary100)
                            ) { Text("Cancel", fontWeight = FontWeight.Bold) }
                        }
                    )
                }
            }
        } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = primary100)
        }
    }
}
