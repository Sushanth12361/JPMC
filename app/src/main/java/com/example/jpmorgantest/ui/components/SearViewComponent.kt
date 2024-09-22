package com.example.jpmorgantest.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.jpmorgantest.R
import com.example.jpmorgantest.ui.theme.Cultured
import com.example.jpmorgantest.util.constants.EMPTY_STRING
import com.example.jpmorgantest.util.constants.ONE

@Composable
fun SearchViewComponent(
    queryUiState: String,
    onQueryChange: (String) -> Unit,
    onGoClicked: () -> Unit = {},
    onClickBackWhenTextIsEmpty: () -> Unit,
    focusRequester: FocusRequester = FocusRequester(),
    hintText: String = EMPTY_STRING
) {
    BackHandler(enabled = queryUiState.isNotEmpty(), onBack = onClickBackWhenTextIsEmpty)

    Box(modifier = Modifier.padding(end = dimensionResource(id = R.dimen.Normal100))) {
        SearchTextField(
            query = queryUiState,
            onQueryChange = onQueryChange,
            onGoClicked = onGoClicked,
            focusRequester = focusRequester,
            hintText = hintText
        )
    }
}

/** TextFieldValue control the cursor position **/
@SuppressWarnings("LongMethod")
@Composable
fun SearchTextField(
    query: String,
    focusRequester: FocusRequester,
    onQueryChange: (String) -> Unit,
    onGoClicked: () -> Unit,
    hintText: String = EMPTY_STRING
) {
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalTextInputService.current
    val dimenSmall150 = dimensionResource(id = R.dimen.Small150)
    var searchTextValue by remember {
        mutableStateOf(
            value = TextFieldValue(
                text = EMPTY_STRING
            )
        )
    }
    searchTextValue = searchTextValue.copy(
        text = query,
        selection = TextRange(query.length)
    )

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.Large125))
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .background(
                Cultured,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.Small150))
            )
            .border(
                width = dimensionResource(id = R.dimen.OneDp),
                color = if (isFocused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary,
                RoundedCornerShape(dimensionResource(id = R.dimen.Small150))
            )
            .focusRequester(focusRequester),
        value = searchTextValue,
        onValueChange = {
            searchTextValue = it
            if (searchTextValue.text != query) {
                onQueryChange(it.text)
            }
        },
        textStyle = MaterialTheme.typography.headlineSmall,
        maxLines = ONE,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Go
        ),
        keyboardActions = KeyboardActions(
            onGo = {
                keyboardController?.hideSoftwareKeyboard()
                onGoClicked()
            }
        ),
        decorationBox = { innerTextField ->
            ConstraintLayout {
                val (searchText, trailingIcon) = createRefs()

                Row(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.Normal100))
                        .constrainAs(searchText) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(trailingIcon.start)
                            width = Dimension.fillToConstraints
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = dimensionResource(id = R.dimen.Small100))
                            .size(dimensionResource(id = R.dimen.Normal125))
                    )

                    Box {
                        innerTextField()

                        if (query.isEmpty() && hintText.isNotEmpty()) {
                            Text(
                                text = hintText,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(start = dimensionResource(id = R.dimen.Small100))
                                    .align(Alignment.CenterStart)
                            )
                        }
                    }
                }

                if (query.isNotEmpty() && isFocused) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_clean_text),
                        contentDescription = null,
                        modifier = Modifier
                            .constrainAs(trailingIcon) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(
                                    parent.end,
                                    margin = dimenSmall150
                                )
                            }
                            .clickable {
                                focusRequester.requestFocus()
                                onQueryChange(EMPTY_STRING)
                            }
                    )
                }
            }
        }
    )
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
fun CustomSearchViewComponentPreview() {
    SearchViewComponent(
        queryUiState = "",
        onQueryChange = {},
        onGoClicked = {},
        onClickBackWhenTextIsEmpty = {},
        hintText = "Search here"
    )
}
