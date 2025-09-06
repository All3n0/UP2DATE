package io.eldohub.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.eldohub.core.ui.theme.grey100

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(94.dp))
        Image(
            painter = painterResource(id = io.eldohub.core.ui.R.drawable.ic_list),
            contentDescription = ""
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text =  stringResource(id = io.eldohub.core.ui.R.string.nothing_found),
            style = MaterialTheme.typography.bodyMedium.copy(
                grey100
            ),
            textAlign = TextAlign.Center
        )
    }
}