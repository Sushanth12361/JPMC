package com.example.jpmorgantest.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jpmorgantest.R
import com.example.jpmorgantest.presentation.detail.components.DetailContent
import com.example.jpmorgantest.presentation.detail.components.TopBarDetailScreen
import com.example.jpmorgantest.ui.components.CustomFullScreenLoading
import com.example.jpmorgantest.ui.components.CustomLottieMessage
import com.example.jpmorgantest.ui.components.CustomSimpleScaffold

@Composable
fun DetailScreen(
    navigateToSearch: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val detailScreen by detailViewModel.weatherDetail.collectAsState()
    val detailStateUI by detailViewModel.detailStateUI.collectAsState()
    CustomSimpleScaffold(
        enableNavigationIcon = false,
        title = {
            TopBarDetailScreen(
                navigateToSearch = navigateToSearch,
                title = detailScreen?.cityName.orEmpty()
            )
        }
    ) {
        if (detailStateUI.isLoading) {
            CustomFullScreenLoading(true)
        } else if (detailStateUI.isError || detailScreen == null) {
            CustomLottieMessage(
                R.raw.animation_error,
                title = stringResource(R.string.til_error),
                message = stringResource(R.string.desc_error),
                showRetryButton = true,
                onClickRetry = detailViewModel::loadRecentlySearched
            )
        } else {
            DetailContent(
                detail = detailScreen
            )
        }
    }
}
