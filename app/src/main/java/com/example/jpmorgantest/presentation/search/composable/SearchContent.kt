package com.example.jpmorgantest.presentation.search.composable

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.jpmorgantest.R
import com.example.jpmorgantest.presentation.search.state.SearchStateUI
import com.example.jpmorgantest.ui.components.CustomPrimaryButton
import com.example.jpmorgantest.ui.permission.PermissionContent
import com.example.jpmorgantest.util.extension.hasLocationPermission
import kotlin.random.Random

@Composable
fun SearchContent(
    searchStateUI: SearchStateUI,
    onLoadLocation: () -> Unit,
    onSaveWeather: () -> Unit,
    activity: Activity
) {
    var launchPermission by rememberSaveable { mutableStateOf<Int?>(null) }
    PermissionContent(
        activity = activity,
        permissionGranted = {
            if (hasLocationPermission(activity)) {
                onLoadLocation()
            }
        },
        launchPermission = launchPermission
    )
    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.Normal100)),
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.Normal100))
    ) {
        if (searchStateUI.isLoading) {
            item {
                LinearProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.FiveDp))
                )
            }
        }
        item {
            CustomPrimaryButton(
                title = stringResource(R.string.desc_current_location),
                textColor = MaterialTheme.colorScheme.background,
                isEnabled = true,
                backGroundColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    if (hasLocationPermission(activity)) {
                        onLoadLocation()
                    } else {
                        launchPermission = Random.nextInt()
                    }
                }
            )
        }
        when {
            (searchStateUI.isError || searchStateUI.isSuccess) &&
                searchStateUI.detailWeather == null -> {
                item {
                    SearchEmptyView()
                }
            }

            searchStateUI.detailWeather != null -> {
                item { WeatherCard(searchStateUI.detailWeather) }
                item {
                    CustomPrimaryButton(
                        title = stringResource(R.string.desc_btn_save_weather),
                        textColor = MaterialTheme.colorScheme.background,
                        isEnabled = true,
                        backGroundColor = MaterialTheme.colorScheme.primary,
                        onClick = onSaveWeather
                    )
                }
            }
        }
    }
}
