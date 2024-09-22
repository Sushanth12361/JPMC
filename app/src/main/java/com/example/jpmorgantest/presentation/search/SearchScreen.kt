package com.example.jpmorgantest.presentation.search

import android.app.Activity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jpmorgantest.R
import com.example.jpmorgantest.presentation.search.composable.SearchContent
import com.example.jpmorgantest.ui.components.CustomExtendedSheetContent
import com.example.jpmorgantest.ui.components.CustomSimpleScaffold
import com.example.jpmorgantest.ui.components.SearchViewComponent
import com.example.jpmorgantest.util.constants.EMPTY_STRING

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navigateToDetail: () -> Unit,
    navigateUp: () -> Unit,
    navController: NavController,
    activity: Activity,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val stateEnableBackIcon = navController.previousBackStackEntry?.destination != null
    val searchStateUI by searchViewModel.searchStateUI.collectAsState()
    val textSearch by searchViewModel.textSearch.collectAsState()
    val isOpenModal by searchViewModel.openBottomSheet.collectAsState()
    CustomSimpleScaffold(
        enableNavigationIcon = stateEnableBackIcon,
        title = {
            SearchViewComponent(
                queryUiState = textSearch,
                onQueryChange = searchViewModel::onQueryChange,
                onClickBackWhenTextIsEmpty = {
                    searchViewModel.onQueryChange(EMPTY_STRING)
                },
                hintText = stringResource(R.string.desc_search_city)
            )
        },
        navigateUp = navigateUp
    ) {
        SearchContent(
            searchStateUI = searchStateUI,
            onLoadLocation = {
                searchViewModel.onLoadLocation(activity)
            },
            onSaveWeather = searchViewModel::onSaveWeatherDetail,
            activity = activity
        )
    }
    if (isOpenModal) {
        ModalBottomSheet(
            shape = RoundedCornerShape(
                topStart = dimensionResource(id = R.dimen.Normal100),
                topEnd = dimensionResource(id = R.dimen.Normal100)
            ),
            content = {
                CustomExtendedSheetContent(
                    icon = R.drawable.ic_wishlist_red,
                    title = stringResource(R.string.til_congratulations),
                    subtitle = stringResource(R.string.desc_congratulations),
                    forceBigSize = true,
                    onClick = {
                        searchViewModel.onCloseBottomSheet()
                        if (stateEnableBackIcon) {
                            navigateUp()
                        } else {
                            navigateToDetail()
                        }
                    },
                    titleButton = stringResource(R.string.til_close)
                )
            },
            onDismissRequest = {
                searchViewModel.onCloseBottomSheet()
                if (stateEnableBackIcon) {
                    navigateUp()
                } else {
                    navigateToDetail()
                }
            }
        )
    }
}
