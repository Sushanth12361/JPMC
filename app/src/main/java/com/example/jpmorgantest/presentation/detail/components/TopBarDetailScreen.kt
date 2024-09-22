package com.example.jpmorgantest.presentation.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jpmorgantest.R
import com.example.jpmorgantest.ui.theme.JPMorganTestTheme

@Composable
fun TopBarDetailScreen(navigateToSearch: () -> Unit, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = navigateToSearch)
            .padding(
                horizontal = dimensionResource(id = R.dimen.Normal100)
            ),
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.Normal100)
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_pin_location),
            contentDescription = stringResource(
                R.string.desc_icon_location
            ),
            modifier = Modifier.size(dimensionResource(id = R.dimen.ImageIcon))
        )
        Column {
            Text(
                text = stringResource(R.string.desc_location),
                style = MaterialTheme.typography.bodySmall
            )
            Text(text = title, style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
fun TopBarDetailScreenPreview() {
    JPMorganTestTheme {
        TopBarDetailScreen(
            navigateToSearch = {},
            "Fairfield"
        )
    }
}
