package io.eldohub.feature.articles.screen.main

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import io.eldohub.core.ui.theme.primary100
import io.eldohub.domain.article.model.Article
import io.eldohub.feature.articles.screen.viewmodels.ArticleViewModel
import java.text.SimpleDateFormat
import java.util.*
// at the top
import androidx.compose.ui.platform.LocalContext
import android.provider.MediaStore
import androidx.compose.ui.graphics.Color

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
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    // Gallery picker
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    // Camera capture
    val context = LocalContext.current

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val uri = Uri.parse(
                MediaStore.Images.Media.insertImage(
                    context.contentResolver,
                    bitmap,
                    "CapturedImage",
                    null
                )
            )
            imageUri = uri
        }
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
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = primary100)
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
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = Color.Red
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = {
                    Text(
                        "Content",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = Color.Red
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            // Image section with gallery + camera
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { pickImageLauncher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = primary100)
                ) {
                    Icon(Icons.Default.PhotoLibrary, contentDescription = "Gallery")
                    Spacer(Modifier.width(8.dp))
                    Text("Pick Image", fontWeight = FontWeight.Bold)
                }
                OutlinedButton(
                    onClick = { takePictureLauncher.launch(null) },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = primary100)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Camera")
                    Spacer(Modifier.width(8.dp))
                    Text("Take Photo", fontWeight = FontWeight.Bold)
                }
            }

            imageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Selected image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val isFormValid = title.isNotBlank() &&
                    content.isNotBlank() &&
                    (!isCompleted || completionDateText.isNotBlank())

            Button(
                onClick = {
                    val completionDate = if (isCompleted && completionDateText.isNotBlank()) {
                        try { dateFormat.parse(completionDateText) } catch (e: Exception) { null }
                    } else null

                    val article = Article(
                        id = 0L,
                        title = title,
                        content = content,
                        dateAdded = Date(),
                        dateCompleted = completionDate,
                        isCompleted = isCompleted,
                        imageUri = imageUri?.toString()
                    )
                    viewModel.addArticle(article)
                    onArticleSaved()
                },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = primary100)
            ) {
                Text("Save Article", fontWeight = FontWeight.Bold)
            }
        }
    }
}
