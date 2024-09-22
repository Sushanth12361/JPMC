package com.example.jpmorgantest.presentation.search.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.example.jpmorgantest.R
import com.example.jpmorgantest.domain.usecase.entities.WeatherDetail
import com.example.jpmorgantest.util.extension.formatImageUrl
import com.example.jpmorgantest.util.extension.orEmpty

@Composable
fun WeatherCard(weather: WeatherDetail?) {
    weather?.let {
        Card(
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.Normal100)),
            elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.Small50)),
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.Normal100))
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.Normal100))) {
                Box(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.ImageMedium))
                        .clip(shape = CircleShape)
                ) {
                    Image(
                        painter = rememberImagePainter(weather.icon.formatImageUrl()),
                        contentDescription = stringResource(
                            R.string.desc_type_img_weather,
                            weather.weatherDescription
                        ),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.Small100)))
                Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.Small50))) {
                    Text(text = weather.cityName, style = MaterialTheme.typography.titleMedium)
                    HorizontalDivider(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.Small50)))
                    Text(
                        text = stringResource(
                            id = R.string.desc_humidity,
                            weather.humidity.orEmpty()
                        ),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = stringResource(
                            R.string.desc_temperature,
                            weather.temperature.orEmpty()
                        ),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
fun CustomCharacterCardPreview() {
    WeatherCard(
        weather = WeatherDetail(
            cityName = "Fairfield",
            temperature = 298.43,
            weatherDescription = "clear sky",
            humidity = 64,
            pressure = 1019,
            windSpeed = 2.58,
            sunrise = 1696330297L,
            sunset = 1696372375L,
            icon = "01d"
        )
    )
}
