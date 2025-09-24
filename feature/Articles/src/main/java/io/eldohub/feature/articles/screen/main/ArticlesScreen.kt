import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import io.eldohub.core.ui.theme.primary100
import io.eldohub.domain.article.model.Article
import io.eldohub.feature.articles.screen.viewmodels.ArticleViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(
    viewModel: ArticleViewModel,
    onArticleClick: (Long) -> Unit,
    onCreateClick: () -> Unit
) {
    val articles by viewModel.articles.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var debouncedQuery by remember { mutableStateOf("") }
    var sortOrder by remember { mutableStateOf(SortOrder.LatestFirst) }
    var expanded by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    var debounceJob by remember { mutableStateOf<Job?>(null) }

    // Debounce search input (200ms)
    LaunchedEffect(searchQuery) {
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(200)
            debouncedQuery = searchQuery
        }
    }

    val sortedArticles = when (sortOrder) {
        SortOrder.EarliestFirst -> articles
            .filter { it.title.contains(debouncedQuery, ignoreCase = true) }
            .sortedBy { it.dateAdded }
        SortOrder.LatestFirst -> articles
            .filter { it.title.contains(debouncedQuery, ignoreCase = true) }
            .sortedByDescending { it.dateAdded }
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
                            text = "My Articles",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onBackground
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
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Create Article Button
            Button(
                onClick = onCreateClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary100,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create")
                Spacer(Modifier.width(8.dp))
                Text("Create Article")
            }

            Spacer(Modifier.height(16.dp))

            // Search Bar with Clear Icon
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear search"
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp), // <-- Rounded corners here
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primary100,
                    unfocusedBorderColor = primary100
                )
            )

            Spacer(Modifier.height(8.dp))
// Sort Dropdown Button
            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, primary100),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = primary100),
                    shape = RoundedCornerShape(12.dp) // Rounded corners for button
                ) {
                    Text(
                        when (sortOrder) {
                            SortOrder.LatestFirst -> "Sort: Latest First"
                            SortOrder.EarliestFirst -> "Sort: Earliest First"
                        }
                    )
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .wrapContentWidth() // only as wide as the content
                        .padding(horizontal = 16.dp), // optional: align with button
                    shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp) // Rounded bottom corners
                ) {
                    DropdownMenuItem(
                        text = { Text("Latest First") },
                        onClick = {
                            sortOrder = SortOrder.LatestFirst
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Earliest First") },
                        onClick = {
                            sortOrder = SortOrder.EarliestFirst
                            expanded = false
                        }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            if (sortedArticles.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No articles found", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(sortedArticles) { article ->
                        ArticleItem(
                            article = article,
                            onClick = { onArticleClick(article.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail if image exists
            article.imageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Article thumbnail",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(12.dp))
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = primary100
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}



enum class SortOrder {
    LatestFirst,
    EarliestFirst
}
