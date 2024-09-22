package com.example.jpmorgantest.presentation.search.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.jpmorgantest.R

@Composable
fun SearchEmptyView() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.Normal100)))
        Icon(
            painter = painterResource(id = R.drawable.ic_alert_warning),
            contentDescription = stringResource(R.string.desc_image_warning),
            tint = Color.Yellow
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.Small100)))
        Text(
            text = stringResource(R.string.til_sorry),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.Small100)))
        Text(
            text = stringResource(R.string.desc_dont_found),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    }
}
